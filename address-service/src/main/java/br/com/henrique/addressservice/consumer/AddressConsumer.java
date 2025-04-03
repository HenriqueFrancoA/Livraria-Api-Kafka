package br.com.henrique.addressservice.consumer;

import br.com.henrique.addressservice.services.AddressService;
import br.com.henrique.addressservice.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class AddressConsumer {

    private static final Logger log = LoggerFactory.getLogger(AddressConsumer.class);
    private final JsonUtil jsonUtil;

    private final AddressService addressService;

    public AddressConsumer(JsonUtil jsonUtil, AddressService addressService) {
        this.jsonUtil = jsonUtil;
        this.addressService = addressService;
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.address-success}"
    )

    public void consumeSuccessEvent(String payload){
        log.info("Receiving success event {} from address-success topic", payload);
        var event = jsonUtil.toEvent(payload);
        addressService.validateAddressSuccess(event);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.address-fail}"
    )

    public void consumeFailEvent(String payload){
        log.info("Receiving rollback event {} from address-fail topic", payload);
        var event = jsonUtil.toEvent(payload);
        addressService.validateAddressFail(event);
    }
}
