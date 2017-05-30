package ru.iav.takoe.countee.vo;

import java.math.BigDecimal;

// TODO make this class implement Validator<Cost>
public class CostValidator {

    void validate(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount must be non-null!");
        }
        if (BigDecimal.ZERO.equals(amount)) {
            throw new IllegalArgumentException("Amount must be non-zero!");
        }
    }

    void validate(String comment) {
        if (comment == null || comment.trim().length() == 0) {
            throw new IllegalArgumentException("Comment most be not empty!");
        }
    }

}
