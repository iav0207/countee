package ru.iav.takoe.countee.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import ru.iav.takoe.countee.da.Reader;
import ru.iav.takoe.countee.vo.Cost;

import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;
import static ru.iav.takoe.countee.utils.ObjectUtils.safeList;

public class CostCommentsService {

    private Reader<Cost> reader;

    @Inject
    public CostCommentsService(Reader<Cost> reader) {
        this.reader = reader;
    }

    /**
     * Excludes entries which have zero costs sum.
     */
    @Nonnull
    public Set<String> getAllCommentsSet() {
        Set<String> commentsSet = new TreeSet<>();
        Map<String, BigDecimal> totals = new HashMap<>();

        List<Cost> allCosts = reader.readAll();
        for (Cost cost : safeList(allCosts)) {
            if (cost == null) {
                continue;
            }

            String key = lowerCaseCommentOf(cost);
            if (commentsSet.contains(key)) {
                BigDecimal newValue = totals.get(key).add(cost.getAmount());
                totals.put(key, newValue);
            } else {
                commentsSet.add(key);
                totals.put(key, cost.getAmount());
            }
        }

        for (Map.Entry<String, BigDecimal> entry : totals.entrySet()) {
            if (isLessThanPlusMinusOne(entry.getValue())) {
                commentsSet.remove(entry.getKey());
            }
        }

        return commentsSet;
    }

    private boolean isLessThanPlusMinusOne(BigDecimal value) {
        return Math.abs(value.doubleValue()) < 1d;
    }

    @Nullable
    private String lowerCaseCommentOf(@Nullable Cost cost) {
        String comment = commentOf(cost);
        return isNull(comment) ? null : comment.toLowerCase();
    }

    @Nullable
    private String commentOf(@Nullable Cost cost) {
        return isNull(cost) ? null : cost.getComment();
    }

}
