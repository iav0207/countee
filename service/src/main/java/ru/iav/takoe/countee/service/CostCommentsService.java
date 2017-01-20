package ru.iav.takoe.countee.service;

import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.TreeSet;

import static java.util.stream.Collectors.toCollection;
import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;
import static ru.iav.takoe.countee.utils.StreamUtils.getStream;

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
        return getStream(reader.readAllCosts())
                .filter(cost -> !isNull(cost))
                .map(Cost::getComment)
                .filter(comment -> !isNull(comment))
                .map(String::toLowerCase)
                .collect(toCollection(TreeSet::new));
    }

}
