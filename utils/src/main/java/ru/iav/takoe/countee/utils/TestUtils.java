package ru.iav.takoe.countee.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;

public class TestUtils {

    public static String getRandomString() {
        return getRandomString(10);
    }

    public static String getRandomString(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static Date getRandomDate() {
        return new Date(getRandomLong());
    }

    public static Date getRandomDateOfLastYear() {
        return DateTime.now()
                .minusMonths(getRandomInteger(12))
                .minusDays(getRandomInteger(30))
                .minusHours(getRandomInteger(24))
                .toDate();
    }

    public static BigDecimal getRandomBigDecimal() {
        return BigDecimal.valueOf(getRandomLong());
    }

    public static Long getRandomLong() {
        return RandomUtils.nextLong(0, Long.MAX_VALUE);
    }

    public static Integer getRandomInteger() {
        return getRandomInteger(Integer.MAX_VALUE);
    }

    public static Integer getRandomInteger(int max) {
        return RandomUtils.nextInt(0, max);
    }

    public static Boolean getRandomBoolean() {
        return RandomUtils.nextInt(0, 2) == 0;
    }

    public static <E extends Enum<E>> E getRandomEnum(Class<E> eClass) {
        E[] literals = eClass.getEnumConstants();
        return literals[RandomUtils.nextInt(0, literals.length)];
    }

}
