package br.com.henrique.orchestratorservice.core.enums;


public enum ETopics {
    START_CREATE_ORDER("start-create-order"),
    BASE_ORCHESTRATOR("orchestrator"),
    FINISH_SUCCESS("finish-success"),
    FINISH_FAIL("finish-fail"),
    PAYMENT_SUCCESS("payment-success"),
    PAYMENT_FAIL("payment-fail"),
    INVENTORY_SUCCESS("inventory-success"),
    INVENTORY_FAIL("inventory-fail"),
    ADDRESS_SUCCESS("address-success"),
    ADDRESS_FAIL("address-fail"),
    USER_SUCCESS("user-success"),
    USER_FAIL("user-fail"),
    ORDER_FAIL("order-fail"),
    NOTIFY_ENDING_CREATE_ORDER("notify-ending-create-order"),

    //refund
    START_REFUND_ORDER("start-refund-order"),
    INVENTORY_SUCCESS_REFUND("inventory-success-refund"),
    INVENTORY_FAIL_REFUND("inventory-fail-refund"),
    ORDER_FAIL_REFUND("order-fail-refund"),
    NOTIFY_ENDING_REFUND_ORDER("notify-ending-refund-order");
    private final String topic;

    ETopics(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

}
