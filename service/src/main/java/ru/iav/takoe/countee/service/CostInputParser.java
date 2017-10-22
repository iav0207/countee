package ru.iav.takoe.countee.service;

import java.math.BigDecimal;

import javax.inject.Inject;

import ru.iav.takoe.countee.service.exception.CostInputValidationException;
import ru.iav.takoe.countee.vo.Cost;
import ru.iav.takoe.countee.vo.CostFactory;

import static ru.iav.takoe.countee.logging.LogService.logError;

public final class CostInputParser {

    private static final String VALIDATION_FAILED_MESSAGE = "Invalid input string format. No record has been saved.";

    private final CostInputValidator validator;

    private final CostFactory costFactory;

    @Inject
    public CostInputParser(CostInputValidator validator, CostFactory costFactory) {
        this.validator = validator;
        this.costFactory = costFactory;
    }

    /**
     * Parse a string into {@link Cost} entity.
     * @param input string, representing a cost.
     * @return Cost formed from the given string.
     * @throws CostInputValidationException if the given string is of invalid format.
     */
    Cost parseAsCost(String input) {
        if (!validator.isValid(input)) {
            logError(VALIDATION_FAILED_MESSAGE);
            throw new CostInputValidationException(VALIDATION_FAILED_MESSAGE);
        }
        String[] s = input.split("\\s+", 2);
        BigDecimal amount = getAmount(s[0]);
        String comment = s[1];
        return costFactory.create(amount, comment);
    }

    private static BigDecimal getAmount(String input) {
        return BigDecimal.valueOf(Double.valueOf(input));
    }

}
