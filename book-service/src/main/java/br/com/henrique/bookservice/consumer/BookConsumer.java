package br.com.henrique.bookservice.consumer;


import br.com.henrique.bookservice.services.BookService;
import br.com.henrique.bookservice.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class BookConsumer {

    private static final Logger log = LoggerFactory.getLogger(BookConsumer.class);
    private final JsonUtil jsonUtil;

    private final BookService bookService;

    public BookConsumer(JsonUtil jsonUtil, BookService bookService) {
        this.jsonUtil = jsonUtil;
        this.bookService = bookService;
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.inventory-success}"
    )

    public void consumeSuccessEvent(String payload){
        log.info("Receiving success event {} from inventory-success topic", payload);
        var event = jsonUtil.toEvent(payload);
        bookService.checkInventoryAvailable(event);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.inventory-fail}"
    )

    public void consumeFailEvent(String payload){
        log.info("Receiving rollback event {} from inventory-fail topic", payload);
        var event = jsonUtil.toEvent(payload);
        bookService.failInventoryAvailable(event);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.inventory-success-refund}"
    )

    public void consumeSuccessRefundEvent(String payload){
        log.info("Receiving success refund event {} from inventory-success-refund topic", payload);
        var event = jsonUtil.toEvent(payload);
        bookService.refundInventoryForOrderCanceled(event);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.inventory-fail-refund}"
    )

    public void consumeFailRefundEvent(String payload){
        log.info("Receiving rollback event {} from inventory-fail-refund topic", payload);
        var event = jsonUtil.toEvent(payload);
        bookService.failRefundInventoryForOrderCanceled(event);
    }

}
