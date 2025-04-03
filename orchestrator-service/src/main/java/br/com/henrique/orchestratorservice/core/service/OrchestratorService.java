package br.com.henrique.orchestratorservice.core.service;

import br.com.henrique.orchestratorservice.core.dto.Event;
import br.com.henrique.orchestratorservice.core.dto.History;
import br.com.henrique.orchestratorservice.core.enums.ETopics;
import br.com.henrique.orchestratorservice.core.producer.SagaOrchestratorProducer;
import br.com.henrique.orchestratorservice.core.saga.SagaExecutionController;
import br.com.henrique.orchestratorservice.core.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static br.com.henrique.orchestratorservice.core.enums.EEventSource.*;
import static br.com.henrique.orchestratorservice.core.enums.ESagaStatus.*;
import static br.com.henrique.orchestratorservice.core.enums.ETopics.NOTIFY_ENDING_CREATE_ORDER;


@Component
public class OrchestratorService {

    private static final Logger log = LoggerFactory.getLogger(OrchestratorService.class);
    private final JsonUtil jsonUtil;

    private final SagaOrchestratorProducer producer;

    private final SagaExecutionController sagaExecutionController;

    public OrchestratorService(JsonUtil jsonUtil, SagaOrchestratorProducer producer, SagaExecutionController sagaExecutionController) {
        this.jsonUtil = jsonUtil;
        this.producer = producer;
        this.sagaExecutionController = sagaExecutionController;
    }

    public void startSaga(Event event){
        event.setSource(ORCHESTRATOR);
        event.setStatus(SUCCESS);

        var topic = getTopic(event);

        log.info("SAGA INICIADA!");
        addHistory(event, "Saga iniciada!");

        sendToProducerWithTopic(event, topic);
    }

    public void finishSagaSuccess(Event event){
        event.setSource(ORCHESTRATOR);
        event.setStatus(SUCCESS);

        log.info("SAGA FINALIZADA COM SUCESSO PARA O EVENTO {}", event.getId());
        addHistory(event, "Saga finalizou com sucesso!");

        notifyFinishedSaga(event);
    }

    public void finishSagaFail(Event event) {
        event.setSource(ORCHESTRATOR);
        event.setStatus(FAIL);

        log.info("SAGA FINALIZADA COM ERROS PARA O EVENTO {}", event.getId());
        addHistory(event, "Saga finalizou com erros!");

        notifyFinishedSaga(event);
    }

    public void continueSaga(Event event) {
        var topic = getTopic(event);

        log.info("SAGA CONTINUANDO PARA O EVENTO {}", event.getId());
        sendToProducerWithTopic(event, topic);
    }

    private ETopics getTopic(Event event){
        return sagaExecutionController.getNextTopic(event);
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

    private void sendToProducerWithTopic(Event event, ETopics topic){
        producer.sendEvent(jsonUtil.toJson(event), topic.getTopic());
    }

    private void notifyFinishedSaga(Event event){
        producer.sendEvent(jsonUtil.toJson(event), NOTIFY_ENDING_CREATE_ORDER.getTopic());
    }
}