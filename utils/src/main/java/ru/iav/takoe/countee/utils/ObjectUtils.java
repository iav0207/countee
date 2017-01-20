package ru.iav.takoe.countee.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by takoe on 12.11.16.
 */
public class ObjectUtils {

    @Nonnull
    public static <T> List<T> defensiveCopy(@Nullable Collection<T> origin) {
        return isNull(origin) ? new ArrayList<>() : origin.stream().collect(toList());
    }

    @Nonnull
    public static <T> List<T> defensiveCopy(@Nullable List<T> origin) {
        return safeList(origin).stream().collect(toList());
    }

    @Nonnull
    public static <T> List<T> safeList(@Nullable List<T> nullableList) {
        return isNull(nullableList) ? new ArrayList<>() : nullableList;
    }

    public static boolean notNull(Object object) {
        return !isNull(object);
    }

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

}
