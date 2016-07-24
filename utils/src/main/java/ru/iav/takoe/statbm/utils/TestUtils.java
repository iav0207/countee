package ru.iav.takoe.statbm.utils;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;

public class TestUtils {

    public static String getRandomString() {
        return getRandomString(10);
    }

    public static String getRandomString(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static BigDecimal getRandomBigDecimal() {
        return BigDecimal.valueOf(getRandomLong());
    }

    public static Long getRandomLong() {
        return RandomUtils.nextLong(0, Long.MAX_VALUE);
    }

    public static Integer getRandomInteger() {
        return RandomUtils.nextInt(0, Integer.MAX_VALUE);
    }

    public static Boolean getRandomBoolean() {
        return RandomUtils.nextInt(0, 2) == 0;
    }

    public static <E extends Enum<E>> E getRandomEnum(Class<E> eClass) {
        E[] literals = eClass.getEnumConstants();
        return literals[RandomUtils.nextInt(0, literals.length)];
    }

}
