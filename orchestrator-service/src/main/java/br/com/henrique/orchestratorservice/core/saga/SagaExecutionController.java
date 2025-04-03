package br.com.henrique.orchestratorservice.core.saga;

import br.com.henrique.orchestratorservice.config.exception.ValidationException;
import br.com.henrique.orchestratorservice.core.dto.Event;
import br.com.henrique.orchestratorservice.core.enums.ETopics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static br.com.henrique.orchestratorservice.core.saga.SagaHandler.*;


@Component
public class SagaExecutionController {

    private static final Logger log = LoggerFactory.getLogger(SagaExecutionController.class);

    public ETopics getNextTopic(Event event) {
        if (event.getSource() == null || event.getStatus() == null || event.getSagaType() == null) {
            throw new ValidationException("Source, status e sagaType precisam ser informados.");
        }

        var topic = findTopicBySourceStatusAndOperation(event);
        logCurrentSaga(event, topic);
        return topic;
    }

    private ETopics findTopicBySourceStatusAndOperation(Event event) {
        return (ETopics) Arrays.stream(SAGA_HANDLER)
                .filter(row -> isEventSourceStatusAndOperationValid(event, row))
                .map(row -> row[TOPIC_INDEX])
                .findFirst()
                .orElseThrow(() -> new ValidationException("Tópico não encontrado!"));
    }

    private boolean isEventSourceStatusAndOperationValid(Event event, Object[] row) {
        var source = row[EVENT_SOURCE_INDEX];
        var status = row[SAGA_STATUS_INDEX];
        var operation = row.length > OPERATION_INDEX ? row[OPERATION_INDEX] : null; // Novo índice

        return event.getSource().equals(source) &&
                event.getStatus().equals(status) &&
                (operation == null || event.getSagaType().equals(operation));
    }

    private void logCurrentSaga(Event event, ETopics topic){
        var sagaId = createSagaId(event);
        var source = event.getSource();
        switch (event.getStatus()){
            case SUCCESS -> log.info("### SAGA ATUAL: {} | SUCESSO | PROXIMO TOPICO {} | {}",
                    source, topic, sagaId);
            case ROLLBACK_PENDING -> log.info("### SAGA ATUAL: {} | ENVIANDO ROLLBACK SERVIÇO ATUAL | PROXIMO TOPICO {} | {}",
                    source, topic, sagaId);
            case FAIL -> log.info("### SAGA ATUAL: {} | ENVIANDO ROLLBACK SERVIÇO ANTERIOR | PROXIMO TOPICO {} | {}",
                    source, topic, sagaId);
        }
    }

    private String createSagaId(Event event){
        return String.format("ORDER ID: %s | TRANSACTION ID %s | EVENT ID %s",
                event.getPayload().getId(), event.getTransactionId(), event.getId());
    }
}
