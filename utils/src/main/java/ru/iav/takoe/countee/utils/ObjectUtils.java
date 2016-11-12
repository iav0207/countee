package ru.iav.takoe.countee.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by takoe on 12.11.16.
 */
public class ObjectUtils {

    public static <T> List<T> defensiveCopy(Collection<T> origin) {
        List<T> copy = new ArrayList<>(origin.size());
        copy.addAll(origin);
        return copy;
    }

    public static <T> List<T> defensiveCopy(List<T> origin) {
        List<T> copy = new ArrayList<>(origin.size());
        copy.addAll(origin);
        return copy;
    }

    public static <T> List<T> safeList(List<T> nullableList) {
        return isNull(nullableList) ? new ArrayList<T>() : nullableList;
    }

    public static boolean isNull(Object object) {
        return object == null;
    }

}
