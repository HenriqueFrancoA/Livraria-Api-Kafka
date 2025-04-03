package br.com.henrique.orchestratorservice.core.consumer;

import br.com.henrique.orchestratorservice.core.service.OrchestratorService;
import br.com.henrique.orchestratorservice.core.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SagaOrchestratorConsumer {

    private static final Logger log = LoggerFactory.getLogger(SagaOrchestratorConsumer.class);
    private final JsonUtil jsonUtil;

    private final OrchestratorService orchestratorService;

    public SagaOrchestratorConsumer(JsonUtil jsonUtil, OrchestratorService orchestratorService) {
        this.jsonUtil = jsonUtil;
        this.orchestratorService = orchestratorService;
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.start-create-order}"
    )

    public void consumeStartSagaEvent(String payload){
        log.info("Recebendo evento {} de start-create-order topico", payload);
        var event = jsonUtil.toEvent(payload);
        orchestratorService.startSaga(event);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.orchestrator}"
    )

    public void consumeOrchestratorEvent(String payload){
        log.info("Recebendo evento {} de orchestrator topico", payload);
        var event = jsonUtil.toEvent(payload);
        orchestratorService.continueSaga(event);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.finish-success}"
    )

    public void consumeFinishSuccessEvent(String payload){
        log.info("Recebendo evento {} de finish-success topico", payload);
        var event = jsonUtil.toEvent(payload);
        orchestratorService.finishSagaSuccess(event);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.finish-fail}"
    )

    public void consumeFinishFailEvent(String payload){
        log.info("Recebendo evento {} de finish-fail topico", payload);
        var event = jsonUtil.toEvent(payload);
        orchestratorService.finishSagaFail(event);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.start-refund-order}"
    )

    public void consumeStartRefundSagaEvent(String payload){
        log.info("Recebendo evento {} de start-refund-order topico", payload);
        var event = jsonUtil.toEvent(payload);
        orchestratorService.startSaga(event);
    }
}
