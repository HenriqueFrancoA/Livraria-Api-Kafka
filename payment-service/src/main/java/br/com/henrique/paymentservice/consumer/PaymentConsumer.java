package br.com.henrique.paymentservice.consumer;

import br.com.henrique.paymentservice.services.PaymentService;
import br.com.henrique.paymentservice.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class PaymentConsumer {

    private static final Logger log = LoggerFactory.getLogger(PaymentConsumer.class);
    private final JsonUtil jsonUtil;

    private final PaymentService paymentService;

    public PaymentConsumer(JsonUtil jsonUtil, PaymentService paymentService) {
        this.jsonUtil = jsonUtil;
        this.paymentService = paymentService;
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.payment-success}"
    )

    public void consumeSuccessEvent(String payload){
        log.info("Receiving success event {} from payment-success topic", payload);
        var event = jsonUtil.toEvent(payload);
        paymentService.createPayment(event);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.payment-fail}"
    )

    public void consumeFailEvent(String payload){
        log.info("Receiving rollback event {} from payment-fail topic", payload);
        var event = jsonUtil.toEvent(payload);
        paymentService.realizeRefund(event);
    }
}
