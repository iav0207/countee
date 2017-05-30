package ru.iav.takoe.countee.vo;

import java.math.BigDecimal;

import javax.inject.Inject;

public class CostFactory {

    // TODO inject Validator<Cost>
    @Inject CostValidator validator;

    public CostFactory() {
    }

    public CostFactory(CostValidator validator) {
        this.validator = validator;
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
