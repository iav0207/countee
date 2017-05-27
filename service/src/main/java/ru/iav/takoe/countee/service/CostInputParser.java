package ru.iav.takoe.countee.service;

import ru.iav.takoe.countee.service.exception.CostInputValidationException;
import ru.iav.takoe.countee.vo.Cost;
import ru.iav.takoe.countee.vo.CostFactory;

import java.math.BigDecimal;

import static ru.iav.takoe.countee.logging.LogService.logError;

class CostInputParser {

    private static final String VALIDATION_FAILED_MESSAGE = "Неверный формат введённой строки. Запись не создана.";

    private static CostInputParser instance;

    private CostInputValidator validator;

    private CostFactory costFactory;

    private CostInputParser() {
        validator = CostInputValidator.getInstance();
        costFactory = CostFactory.getInstance();
    }

    static CostInputParser getInstance() {
        if (instance == null) {
            instance = new CostInputParser();
        }
        return instance;
    }

    Cost parseAsCost(String input) throws CostInputValidationException {
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
