package ru.iav.takoe.countee.vo;

import java.math.BigDecimal;

public class CostFactory {

    private static CostFactory instance;

    private CostValidator validator;

    public static CostFactory getInstance() {
        if (instance == null) {
            instance = new CostFactory();
        }
        return instance;
    }

    private CostFactory() {
        validator = CostValidator.getInstance();
    }

    public Cost create(BigDecimal amount, String comment) throws IllegalArgumentException {
        try {
            validator.validate(amount);
            validator.validate(comment);
            return new Cost(amount, comment);
        } catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException(iae.getMessage() + " Cost instance was not constructed.");
        }
    }

}
