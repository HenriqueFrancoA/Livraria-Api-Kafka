package br.com.henrique.orderservice.services;

import br.com.henrique.orderservice.enums.OrderStatus;
import br.com.henrique.orderservice.exceptions.StatusException;
import br.com.henrique.orderservice.mapper.DozerMapper;
import br.com.henrique.orderservice.models.Event;
import br.com.henrique.orderservice.models.History;
import br.com.henrique.orderservice.models.ItemOrder;
import br.com.henrique.orderservice.models.Order;
import br.com.henrique.orderservice.models.dto.ItemOrderListDto;
import br.com.henrique.orderservice.models.dto.OrderDto;
import br.com.henrique.orderservice.models.dto.OrderStatusDto;
import br.com.henrique.orderservice.models.dto.OrderWithUserAddressDto;
import br.com.henrique.orderservice.producer.SagaProducer;
import br.com.henrique.orderservice.repositories.OrderRepository;
import br.com.henrique.orderservice.utils.JsonUtil;
import br.com.henrique.orderservice.utils.ValidationUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static br.com.henrique.orderservice.enums.ESagaStatus.FAIL;
import static br.com.henrique.orderservice.enums.OrderStatus.CANCELED;
import static br.com.henrique.orderservice.enums.OrderStatus.PENDING;

@Service
public class OrderService {

    private final Logger logger = Logger.getLogger(OrderService.class.getName());

    private static final String TRANSACTION_ID_PATTERM = "%s_%s";

    private static final String CURRENT_SOURCE = "ORDER_SERVICE";

    private final EventService eventService;
    private final SagaProducer sagaProducer;
    private final JsonUtil jsonUtil;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemOrderService itemOrderService;

    public OrderService(EventService eventService, SagaProducer sagaProducer, JsonUtil jsonUtil) {
        this.eventService = eventService;
        this.sagaProducer = sagaProducer;
        this.jsonUtil = jsonUtil;
    }

    public Order updateStatus(Long id, OrderStatusDto orderStatus) {
        logger.info("Atualizando status do Pedido, ID: " + id);

        ValidationUtils.verifyOrderStatus(orderStatus.getStatus());

        Order ord = orderRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("ID não encontrado."));

        if(verifyNotPending(ord.getStatus()))
            throw new StatusException("O pedido já foi " + ord.getStatus() + " e não pode ser atualizado.");

        if(verifyCanceledOrRejected(orderStatus.getStatus())){
            ord.setItems(itemOrderService.findByOrderId(ord.getId()));

            sagaProducer.sendEventRefund(jsonUtil.toJson(createPayload(ord, "REFUND")));
        }
        ord.setStatus(orderStatus.getStatus());
        orderRepository.save(ord);

        return ord;
    }

    public boolean verifyNotPending(String status){
        return !status.equals(PENDING.getDescription());
    }

    public boolean verifyCanceledOrRejected(String status){
        return status.equals(OrderStatus.REJECTED.getDescription()) ||
                status.equals(CANCELED.getDescription());
    }

    public Order create(OrderWithUserAddressDto orderWithUserAddressDto) {
        logger.info("Criando pedido...");

        Order order = new Order(
                String.format(TRANSACTION_ID_PATTERM, Instant.now().toEpochMilli(), UUID.randomUUID()),
                orderWithUserAddressDto.getUserId(),
                orderWithUserAddressDto.getAddressId(),
                LocalDateTime.now(),
                PENDING.getDescription()
        );

        Order orderCreated = orderRepository.save(order);

        try {
            var itemOrder = new ItemOrderListDto(
                    orderCreated.getId(),
                    orderWithUserAddressDto.getItems()
            );

            List<ItemOrder> items = itemOrderService.addItem(itemOrder);
            orderCreated.setItems(items);

            sagaProducer.sendEvent(jsonUtil.toJson(createPayload(orderCreated, "CREATE")));

        } catch (Exception e) {
            orderCreated.setStatus(CANCELED.getDescription());
            orderRepository.save(orderCreated);
        }
        return orderCreated;
    }

    public Order findById(Long id) {
        logger.info("Procurando pedido do ID: " + id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException("ID do Pedido não encontrado."));

        List<ItemOrder> listItens = itemOrderService.findByOrderId(order.getId());
        order.setItems(listItens);

        return order;
    }

    public List<Order> findByUser(Long id) {
        logger.info("Procurando pedidos do User: " + id);
        List<Order> listOrder = orderRepository.findByUserId(id);

        for(Order order : listOrder){
            List<ItemOrder> listItens = itemOrderService.findByOrderId(order.getId());
            order.setItems(listItens);
        }

        return listOrder;
    }

    private Event createPayload(Order order, String sagaType){
        var event = new Event(
                order.getTransactionId(),
                LocalDateTime.now(),
                order.getId(),
                order,
                sagaType
        );

        eventService.save(event);
        return event;
    }

    private void addHistory(Event event, String message){
        var history = new History(
                event.getSource(),
                event.getStatus(),
                message,
                LocalDateTime.now()
        );
        event.addToHistory(history);
    }

    public void realizeCancelOrder(Event event) {
        event.setStatus(FAIL.name());
        event.setSource(CURRENT_SOURCE);

        try {
            changeOrderStatusCancel(event);
            addHistory(event, "Rollback executado para order");
        } catch (Exception ex) {
            addHistory(event, "Rollback não executado para order: " + ex.getMessage());
        }

        sagaProducer.sendEventOrchestrator(jsonUtil.toJson(event));
    }

    public void realizeCancelRefund(Event event) {
        event.setStatus(FAIL.name());
        event.setSource(CURRENT_SOURCE);

        try {
            changeOrderStatusPending(event);
            addHistory(event, "Rollback executado para order");
        } catch (Exception ex) {
            addHistory(event, "Rollback não executado para order: " + ex.getMessage());
        }

        sagaProducer.sendEventOrchestrator(jsonUtil.toJson(event));
    }

    private void changeOrderStatusCancel(Event event) {
        var order = orderRepository.findById(event.getOrderId()).orElseThrow();

        order.setStatus(CANCELED.getDescription());
        orderRepository.save(order);
    }

    private void changeOrderStatusPending(Event event) {
        var order = orderRepository.findById(event.getOrderId()).orElseThrow();

        order.setStatus(PENDING.getDescription());
        orderRepository.save(order);
    }

}
