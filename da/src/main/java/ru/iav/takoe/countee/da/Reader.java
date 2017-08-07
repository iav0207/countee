package ru.iav.takoe.countee.da;

import java.util.List;

import javax.annotation.Nonnull;

import ru.iav.takoe.countee.vo.Cost;

public interface Reader<T> {

    @Nonnull List<Cost> readForThisMonth();

    @Nonnull List<Cost> readAll();

}
