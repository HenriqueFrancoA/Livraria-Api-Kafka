package br.com.henrique.orderservice.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SagaProducer {

    private static final Logger log = LoggerFactory.getLogger(SagaProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.start-create-order}")
    private String startCreateOrderTopic;

    @Value("${spring.kafka.topic.start-refund-order}")
    private String startRefundOrderTopic;

    @Value("${spring.kafka.topic.orchestrator}")
    private String orchestratorTopic;

    public SagaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(String payload){
        try {
            log.info("Sending event to topic {} with data {}", startCreateOrderTopic, payload);
            kafkaTemplate.send(startCreateOrderTopic, payload);
        } catch (Exception ex) {
            log.error("Error trying to send data to topic {} with data {}", startCreateOrderTopic, payload, ex);
        }
    }

    public void sendEventRefund(String payload){
        try {
            log.info("Sending event to topic {} with data {}", startRefundOrderTopic, payload);
            kafkaTemplate.send(startRefundOrderTopic, payload);
        } catch (Exception ex) {
            log.error("Error trying to send data to topic {} with data {}", startRefundOrderTopic, payload, ex);
        }
    }

    public void sendEventOrchestrator(String payload){
        try {
            log.info("Sending event to topic {} with data {}", orchestratorTopic, payload);
            kafkaTemplate.send(orchestratorTopic, payload);
        } catch (Exception ex) {
            log.error("Error trying to send data to topic {} with data {}", orchestratorTopic, payload, ex);
        }
    }
}
