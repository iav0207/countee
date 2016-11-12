package ru.iav.takoe.countee.utils;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by takoe on 24.07.16.
 */
public class DateUtils {

    public static DateTime today() {
        return day(now());
    }

    public static DateTime day(Date date) {
        return new DateTime(date).withTimeAtStartOfDay();
    }

    public static Date now() {
        return new Date();
    }

}
