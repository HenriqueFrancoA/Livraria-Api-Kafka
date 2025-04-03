package br.com.henrique.orchestratorservice.core.saga;

import static br.com.henrique.orchestratorservice.core.enums.EEventSource.*;
import static br.com.henrique.orchestratorservice.core.enums.ESagaStatus.*;
import static br.com.henrique.orchestratorservice.core.enums.ETopics.*;

public final class SagaHandler {

    private SagaHandler(){

    }

    public static final Object[][] SAGA_HANDLER = {
            // Fluxo de criação (CREATE)
            { ORCHESTRATOR, SUCCESS, PAYMENT_SUCCESS, "CREATE" },
            { ORCHESTRATOR, FAIL, FINISH_FAIL, "CREATE" },

            { ORDER_SERVICE, ROLLBACK_PENDING, ORDER_FAIL, "CREATE" },
            { ORDER_SERVICE, FAIL, FINISH_FAIL, "CREATE" },

            { PAYMENT_SERVICE, ROLLBACK_PENDING, PAYMENT_FAIL, "CREATE" },
            { PAYMENT_SERVICE, FAIL, ORDER_FAIL, "CREATE" },
            { PAYMENT_SERVICE, SUCCESS, INVENTORY_SUCCESS, "CREATE" },

            { BOOK_SERVICE, ROLLBACK_PENDING, INVENTORY_FAIL, "CREATE" },
            { BOOK_SERVICE, FAIL, PAYMENT_FAIL, "CREATE" },
            { BOOK_SERVICE, SUCCESS, ADDRESS_SUCCESS, "CREATE" },

            { ADDRESS_SERVICE, ROLLBACK_PENDING, ADDRESS_FAIL, "CREATE" },
            { ADDRESS_SERVICE, FAIL, INVENTORY_FAIL, "CREATE" },
            { ADDRESS_SERVICE, SUCCESS, USER_SUCCESS, "CREATE" },

            { USER_SERVICE, ROLLBACK_PENDING, USER_FAIL, "CREATE" },
            { USER_SERVICE, FAIL, ADDRESS_FAIL, "CREATE" },
            { USER_SERVICE, SUCCESS, FINISH_SUCCESS, "CREATE" },

            // Fluxo de refund (REFUND)
            { ORCHESTRATOR, SUCCESS, INVENTORY_SUCCESS_REFUND, "REFUND" },
            { ORCHESTRATOR, FAIL, FINISH_FAIL, "REFUND" },

            { BOOK_SERVICE, ROLLBACK_PENDING, INVENTORY_FAIL_REFUND, "REFUND" },
            { BOOK_SERVICE, FAIL, ORDER_FAIL_REFUND, "REFUND" },
            { BOOK_SERVICE, SUCCESS, FINISH_SUCCESS, "REFUND" },

            { ORDER_SERVICE, ROLLBACK_PENDING, ORDER_FAIL_REFUND, "REFUND" },
            { ORDER_SERVICE, FAIL, FINISH_FAIL, "REFUND" },
    };

    public static final int EVENT_SOURCE_INDEX = 0;
    public static final int SAGA_STATUS_INDEX = 1;
    public static final int TOPIC_INDEX = 2;
    public static final int OPERATION_INDEX = 3;
}
