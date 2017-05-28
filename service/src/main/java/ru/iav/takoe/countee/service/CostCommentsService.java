package ru.iav.takoe.countee.service;

import java.util.List;
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

    @Nonnull
    public Set<String> getAllCommentsSet() {
        Set<String> commentsSet = new TreeSet<>();
        List<Cost> allCosts = reader.readAllCosts();
        for (Cost each : safeList(allCosts)) {
            commentsSet.add(lowerCaseCommentOf(each));
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
