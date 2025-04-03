package br.com.henrique.orderservice.consumer;

import br.com.henrique.orderservice.services.EventService;
import br.com.henrique.orderservice.services.OrderService;
import br.com.henrique.orderservice.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {

    private static final Logger log = LoggerFactory.getLogger(EventConsumer.class);

    private final JsonUtil jsonUtil;
    private final EventService eventService;
    private final OrderService orderService;

    public EventConsumer(JsonUtil jsonUtil, EventService eventService, OrderService orderService) {
        this.jsonUtil = jsonUtil;
        this.eventService = eventService;
        this.orderService = orderService;
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.notify-ending-create-order}"
    )

    public void consumeNotifyEndingCreateOrderEvent(String payload){
        log.info("Receiving ending notification event {} from notify-ending topic", payload);
        var event = jsonUtil.toEvent(payload);
        eventService.notifyEnding(event);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.order-fail}"
    )

    public void consumeFailEvent(String payload){
        log.info("Receiving rollback event {} from order-fail topic", payload);
        var event = jsonUtil.toEvent(payload);
        orderService.realizeCancelOrder(event);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.notify-ending-refund-order}"
    )

    public void consumeNotifyEndingRefundOrderEvent(String payload){
        log.info("Receiving ending notification event {} from notify-ending-refund topic", payload);
        var event = jsonUtil.toEvent(payload);
        eventService.notifyEnding(event);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.order-fail-refund}"
    )

    public void consumeFailRefundEvent(String payload){
        log.info("Receiving rollback event {} from order-fail-refund topic", payload);
        var event = jsonUtil.toEvent(payload);
        orderService.realizeCancelOrder(event);
    }
}
