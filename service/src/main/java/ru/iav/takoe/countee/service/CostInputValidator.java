package ru.iav.takoe.countee.service;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

public class CostInputValidator {

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
