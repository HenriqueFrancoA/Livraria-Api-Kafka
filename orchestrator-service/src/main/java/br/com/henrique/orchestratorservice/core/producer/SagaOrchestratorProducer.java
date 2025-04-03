package br.com.henrique.orchestratorservice.core.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SagaOrchestratorProducer {

    private static final Logger log = LoggerFactory.getLogger(SagaOrchestratorProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public SagaOrchestratorProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(String payload, String topic){
        try {
            log.info("Enviando evento para o topico {} com dados {}", topic, payload);
            kafkaTemplate.send(topic, payload);
        } catch (Exception ex) {
            log.error("Erro tentando enviar dados para topico {} com dados {}", topic, payload, ex);
        }
    }
}
