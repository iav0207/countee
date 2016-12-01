package ru.iav.takoe.countee.model.filter;

import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by takoe on 01.12.16.
 */
public interface CostFilter {

    @Nonnull List<Cost> filter(@Nullable List<Cost> costs);

}
