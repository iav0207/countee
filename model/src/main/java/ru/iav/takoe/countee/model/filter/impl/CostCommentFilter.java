package ru.iav.takoe.countee.model.filter.impl;

import ru.iav.takoe.countee.model.filter.CostFilter;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.defaultString;
import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;
import static ru.iav.takoe.countee.utils.ObjectUtils.safeList;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

public class CostCommentFilter implements CostFilter {

    private static final String DEFAULT_STRING = getRandomString(30);

    private final Set<String> commentsToLeave;

    private CostCommentFilter(Collection<String> comments) {
        commentsToLeave = new HashSet<>();
        for (String each : comments) {
            addLowerCase(each);
        }
    }

    private void addLowerCase(@Nullable String comment) {
        if (!isNull(comment)) {
            commentsToLeave.add(comment.toLowerCase());
        }
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
        List<Cost> filteredList = new ArrayList<>();
        for (Cost each : safeList(costs)) {
            addToListIfCorresponds(each, filteredList);
        }
        return filteredList;
    }

    private void addToListIfCorresponds(@Nullable Cost cost, @Nonnull List<Cost> list) {
        if (doesCorrespondToFilter(commentOf(cost))) {
            list.add(cost);
        }
    }

    private boolean doesCorrespondToFilter(@Nonnull String comment) {
        return commentsToLeave.contains(comment.toLowerCase());
    }

    @Nonnull
    private static String commentOf(@Nullable Cost cost) {
        return isNull(cost) ? DEFAULT_STRING : defaultString(cost.getComment());
    }

}
