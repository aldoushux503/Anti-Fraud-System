package com.example.antifraudsystem.component;

import org.springframework.stereotype.Component;

@Component
public class LuhnAlgorithm {

    public boolean validateCardNumber(String cardNumber) {
        int sum = 0;
        for (int i = 0; i < cardNumber.length(); i++) {
            int number = Character.getNumericValue(cardNumber.charAt(i));

            if (i % 2 == 0) {
                number *= 2;
                number = number > 9 ? number - 9 : number;
            }
            sum += number;
        }

        System.out.println(sum);
        return sum % 10 == 0;
    }
}
