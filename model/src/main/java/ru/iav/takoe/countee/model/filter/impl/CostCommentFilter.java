package ru.iav.takoe.countee.model.filter.impl;

import ru.iav.takoe.countee.model.filter.CostFilter;
import ru.iav.takoe.countee.utils.ObjectUtils;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.defaultString;
import static ru.iav.takoe.countee.utils.StreamUtils.getStream;

/**
 * Created by takoe on 30.11.16.
 */
public class CostCommentFilter implements CostFilter {

    private final Set<String> commentsToLeave;

    private CostCommentFilter(Collection<String> comments) {
        commentsToLeave = new HashSet<>();
        getStream(comments)
                .filter(ObjectUtils::notNull)
                .map(String::toLowerCase)
                .forEach(commentsToLeave::add);
    }

    public static CostCommentFilter from(@Nonnull String... comments) {
        return new CostCommentFilter(Arrays.asList(comments));
    }

    public static CostCommentFilter from(@Nonnull Collection<String> comments) {
        return new CostCommentFilter(comments);
    }

    @Override
    @Nonnull
    public List<Cost> filter(@Nullable List<Cost> costs) {
        return getStream(costs)
                .filter(ObjectUtils::notNull)
                .filter(cost -> doesCorrespondToFilter(commentOf(cost)))
                .collect(toList());
    }

    @Nonnull
    private static String commentOf(@Nonnull Cost cost) {
        return defaultString(cost.getComment());
    }

    private boolean doesCorrespondToFilter(@Nonnull String comment) {
        return commentsToLeave.contains(comment.toLowerCase());
    }

}
