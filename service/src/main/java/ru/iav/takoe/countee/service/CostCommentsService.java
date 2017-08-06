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

import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.vo.Cost;

import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;
import static ru.iav.takoe.countee.utils.ObjectUtils.safeList;

public class CostCommentsService {

    private CostReader reader;

    @Inject
    public CostCommentsService(CostReader reader) {
        this.reader = reader;
    }

    /**
     * Excludes entries which have zero costs sum.
     */
    @Nonnull
    public Set<String> getAllCommentsSet() {
        Set<String> commentsSet = new TreeSet<>();
        Map<String, BigDecimal> totals = new HashMap<>();

        List<Cost> allCosts = reader.readAllCosts();
        for (Cost cost : safeList(allCosts)) {
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
            if (BigDecimal.ZERO.equals(entry.getValue())) {
                commentsSet.remove(entry.getKey());
            }
        }

        return commentsSet;
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
