package ru.iav.takoe.countee.utils;

import org.joda.time.DateTime;

import java.util.Date;

public class DateUtils {

    public static DateTime today() {
        return day(now());
    }

    public static DateTime month(Date date) {
        return day(date).withDayOfMonth(1);
    }

    public static DateTime month(DateTime date) {
        return date.withTimeAtStartOfDay().withDayOfMonth(1);
    }

    public static DateTime day(Date date) {
        return new DateTime(date).withTimeAtStartOfDay();
    }

    public static Date now() {
        return new Date();
    }

}
