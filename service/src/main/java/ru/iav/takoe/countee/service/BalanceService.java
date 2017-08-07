package ru.iav.takoe.countee.service;

import java.math.BigDecimal;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import ru.iav.takoe.countee.da.Reader;
import ru.iav.takoe.countee.model.BalanceCalculator;
import ru.iav.takoe.countee.vo.Cost;

public class BalanceService {

    private final Reader<Cost> reader;

    private final BalanceCalculator calculator;

    @Inject
    public BalanceService(Reader<Cost> reader, BalanceCalculator calculator) {
        this.reader = reader;
        this.calculator = calculator;
    }

    @Nonnull
    public BigDecimal getCurrentBalance() {
        return getBalance(reader.readAll());
    }

    @Nonnull
    public BigDecimal getBalance(@Nullable Collection<Cost> costs) {
        return calculator.getBalance(costs);
    }


}
