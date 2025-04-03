package br.com.henrique.authservice.consumer;

import br.com.henrique.authservice.models.dto.Event;
import br.com.henrique.authservice.services.UserService;
import br.com.henrique.authservice.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class UserConsumer {

    private static final Logger log = LoggerFactory.getLogger(UserConsumer.class);
    private final JsonUtil jsonUtil;

    private final UserService userService;

    public UserConsumer(JsonUtil jsonUtil, UserService userService) {
        this.jsonUtil = jsonUtil;
        this.userService = userService;
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.user-success}"
    )

    public void consumeSuccessEvent(String payload){
        log.info("Receiving success event {} from user-success topic", payload);
        var event = jsonUtil.toEvent(payload);
        userService.validateUserSuccess(event);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.user-fail}"
    )

    public void consumeFailEvent(String payload){
        log.info("Receiving rollback event {} from user-fail topic", payload);
        var event = jsonUtil.toEvent(payload);
        userService.validateUserFail(event);
    }
}
