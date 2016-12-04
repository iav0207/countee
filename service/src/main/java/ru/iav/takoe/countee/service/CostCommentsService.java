package ru.iav.takoe.countee.service;

import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;
import static ru.iav.takoe.countee.utils.ObjectUtils.safeList;

/**
 * Created by takoe on 01.12.16.
 */
public class CostCommentsService {

    private static CostCommentsService ourInstance = new CostCommentsService();

    public static CostCommentsService getInstance() {
        return ourInstance;
    }

    private CostReader reader;

    private CostCommentsService() {
        reader = CostReader.getInstance();
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
