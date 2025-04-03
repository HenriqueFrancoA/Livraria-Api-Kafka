package br.com.henrique.orderservice.services;

import br.com.henrique.orderservice.exceptions.ListEmptyException;
import br.com.henrique.orderservice.mapper.DozerMapper;
import br.com.henrique.orderservice.models.dto.*;
import br.com.henrique.orderservice.models.ItemOrder;
import br.com.henrique.orderservice.models.Order;
import br.com.henrique.orderservice.repositories.ItemOrderRepository;
import br.com.henrique.orderservice.repositories.OrderRepository;
import br.com.henrique.orderservice.utils.ValidationUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ItemOrderService {

    private final Logger logger = Logger.getLogger(ItemOrderService.class.getName());

    @Autowired
    private ItemOrderRepository itemOrderRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<ItemOrder> addItem(ItemOrderListDto itemOrderListDto) {
        logger.info("Adicionando item ao pedido de ID: " + itemOrderListDto.getOrderId());

        if(itemOrderListDto.getItems() == null
                || itemOrderListDto.getItems().isEmpty())
            throw new ListEmptyException("Lista de Itens está vazia.");

        if(!orderRepository.existsById(itemOrderListDto.getOrderId()))
                throw new EntityNotFoundException("Pedido não encontrado.");

        ItemOrder itemOrder;
        List<ItemOrder> items = new ArrayList<>();
        for (ItemOrderWithBookDto item : itemOrderListDto.getItems()){

            ValidationUtils.verifyValue(item.getUnitPrice());
            ValidationUtils.verifyQuantity(item.getQuantity());

            itemOrder = new ItemOrder(
                itemOrderListDto.getOrderId(),
                item.getBookId(),
                item.getQuantity(),
                item.getUnitPrice()
            );

            itemOrderRepository.save(itemOrder);
            items.add(itemOrder);
        }
        return items;
    }

    public ItemOrderDto findById(Long id) {
        logger.info("Procurando Item do ID: " + id);
        ItemOrder item = itemOrderRepository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException("ID do Item não encontrado."));

        return DozerMapper.parseObject(item, ItemOrderDto.class);
    }

    public List<ItemOrder> findByOrderId(Long orderId){
        return itemOrderRepository.findByOrderId(orderId);
    }
}
