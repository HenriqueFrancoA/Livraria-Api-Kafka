package br.com.henrique.orderservice.utils;

import br.com.henrique.orderservice.enums.OrderStatus;
import br.com.henrique.orderservice.enums.PaymentMethod;
import br.com.henrique.orderservice.enums.PaymentStatus;
import br.com.henrique.orderservice.exceptions.InvalidPaymentMethodException;
import br.com.henrique.orderservice.exceptions.InvalidStatusException;
import br.com.henrique.orderservice.exceptions.StatusException;

import java.math.BigDecimal;

public class ValidationUtils {

    public static void verifyOrderStatus(String status) {
        if (!status.equals(OrderStatus.PENDING.getDescription()) &&
                !status.equals(OrderStatus.APPROVED.getDescription()) &&
                !status.equals(OrderStatus.CANCELED.getDescription()) &&
                !status.equals(OrderStatus.REJECTED.getDescription())) {
            throw new InvalidStatusException("Insira um status correto, como: "
                    + OrderStatus.PENDING.getDescription() + ", "
                    + OrderStatus.APPROVED.getDescription() + ", "
                    + OrderStatus.CANCELED.getDescription() + " ou "
                    + OrderStatus.REJECTED.getDescription());
        }
    }

    public static void verifyPending(String status){
        if(!status.equals(OrderStatus.PENDING.getDescription()))
            throw new StatusException("O pedido não pode ser atualizado, pois seu status se encontra: " + status);

    }

    public static void verifyWaiting(String status){
        if(!status.equals(PaymentStatus.WAITING.getDescription()))
            throw new StatusException("O pedido não pode ser atualizado, pois seu status do pagamaneto se encontra: " + status);

    }

    public static void verifyPaymentMethod(String paymentMethod) {
        if (!paymentMethod.equals(PaymentMethod.CREDIT.getDescription()) &&
                !paymentMethod.equals(PaymentMethod.DEBIT.getDescription()) &&
                !paymentMethod.equals(PaymentMethod.PIX.getDescription()) &&
                !paymentMethod.equals(PaymentMethod.TICKET.getDescription())) {
            throw new InvalidPaymentMethodException("Insira um método de pagamento correto, como: "
                    + PaymentMethod.CREDIT.getDescription() + ", "
                    + PaymentMethod.DEBIT.getDescription() + ", "
                    + PaymentMethod.TICKET.getDescription() + " ou "
                    + PaymentMethod.PIX.getDescription());
        }
    }

    public static int verifyBookQuantity(int bookQuantity, int quantity){
        int bookQuantityUpdated = bookQuantity - quantity;

        if(bookQuantityUpdated < 0)
            throw new IllegalArgumentException("A quantidade inserida não pode ser maior que a quantidade de livros no estoque.");

        return bookQuantityUpdated;
    }

    public static void verifyValue(BigDecimal value){
        if(value.doubleValue() < 0)
            throw new IllegalArgumentException("O valor não pode ser menor que 0.");
    }

    public static void verifyQuantity(int quantity){
        if(quantity <= 0)
            throw new IllegalArgumentException("A quantidade inserida tem que ser maior ou igual que 0.");
    }
}
