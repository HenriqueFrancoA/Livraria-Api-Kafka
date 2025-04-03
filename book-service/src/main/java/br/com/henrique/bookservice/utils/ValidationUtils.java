package br.com.henrique.bookservice.utils;

import java.math.BigDecimal;

public class ValidationUtils {

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
