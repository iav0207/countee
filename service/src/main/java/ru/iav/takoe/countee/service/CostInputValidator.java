package ru.iav.takoe.countee.service;

import java.util.regex.Pattern;

import javax.annotation.Nullable;

public final class CostInputValidator {

    private static final String FORMAT = "^([-])?[0-9]+([.][0-9]{1,5})?\\s+\\w+";

    boolean isValid(@Nullable String costInput) {
        return Pattern.matches(FORMAT, costInput);
    }

}
