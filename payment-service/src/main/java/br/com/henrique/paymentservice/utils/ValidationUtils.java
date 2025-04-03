package br.com.henrique.paymentservice.utils;

import br.com.henrique.paymentservice.enums.PaymentMethod;
import br.com.henrique.paymentservice.enums.PaymentStatus;
import br.com.henrique.paymentservice.exceptions.InvalidPaymentMethodException;
import br.com.henrique.paymentservice.exceptions.InvalidStatusException;
import br.com.henrique.paymentservice.exceptions.StatusException;

import java.math.BigDecimal;

import static br.com.henrique.paymentservice.enums.PaymentMethod.*;
import static br.com.henrique.paymentservice.enums.PaymentStatus.*;

public class ValidationUtils {

    public static void verifyPaymentStatus(String status) {
        if (!status.equals(WAITING.getDescription()) &&
                !status.equals(APPROVED.getDescription()) &&
                !status.equals(REFUNDED.getDescription()) &&
                !status.equals(REJECTED.getDescription())) {
            throw new InvalidStatusException("Insira um status correto, como: "
                    + WAITING.getDescription() + ", "
                    + APPROVED.getDescription() + ", "
                    + REFUNDED.getDescription() + " ou "
                    + REJECTED.getDescription());
        }
    }

    public static void verifyWaiting(String status){
        if(!status.equals(WAITING.getDescription()))
            throw new StatusException("O pedido não pode ser atualizado, pois seu status do pagamaneto se encontra: " + status);

    }

    public static void verifyPaymentMethod(String paymentMethod) {
        if (!paymentMethod.equals(CREDIT.getDescription()) &&
                !paymentMethod.equals(DEBIT.getDescription()) &&
                !paymentMethod.equals(PIX.getDescription()) &&
                !paymentMethod.equals(TICKET.getDescription())) {
            throw new InvalidPaymentMethodException("Insira um método de pagamento correto, como: "
                    + CREDIT.getDescription() + ", "
                    + DEBIT.getDescription() + ", "
                    + TICKET.getDescription() + " ou "
                    + PIX.getDescription());
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
