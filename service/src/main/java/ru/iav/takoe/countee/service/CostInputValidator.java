package ru.iav.takoe.countee.service;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Created by takoe on 28.07.16.
 */
class CostInputValidator {

    private static CostInputValidator instance;

    private CostInputValidator() {}

    static CostInputValidator getInstance() {
        if (instance == null) {
            instance = new CostInputValidator();
        }
        return instance;
    }

    boolean isValid(@Nullable String costInput) {
        if (StringUtils.isBlank(costInput) || !costInput.contains(" ")) {
            return false;
        }
        String[] s = costInput.split("\\s+", 2);
        return isValidAmountPart(s[0]) && isValidCommentPart(s[1]);
    }

    private boolean isValidAmountPart(String s) {
        try {
            BigDecimal number = BigDecimal.valueOf(Double.valueOf(s));
            return number != null;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidCommentPart(String s) {
        return !StringUtils.isBlank(s) && !s.contains(" ");
    }

}
