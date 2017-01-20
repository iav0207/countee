package ru.iav.takoe.countee.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

/**
 * Created by takoe on 15.01.17.
 */
public class StreamUtils {

    @Nonnull
    public static <T> Stream<T> getStream(@Nullable Collection<T> collection) {
        return isNull(collection) ? Stream.empty() : collection.stream();
    }

    @Nonnull
    public static <T> Stream<T> reverse(@Nonnull Stream<T> stream) {
        LinkedList<T> stack = new LinkedList<>();
        stream.forEach(stack::push);
        return stack.stream();
    }

}
