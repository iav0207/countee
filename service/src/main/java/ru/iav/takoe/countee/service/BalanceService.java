package ru.iav.takoe.countee.service;

import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.model.BalanceCalculator;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;

public class BalanceService {

    private static BalanceService instance = new BalanceService();

    private CostReader reader;

    private BalanceCalculator calculator;

    private BalanceService() {
        reader = CostReader.getInstance();
        calculator = BalanceCalculator.getInstance();
    }

    public static BalanceService getInstance() {
        return instance;
    }

    @Nonnull
    public BigDecimal getCurrentBalance() {
        return getBalance(reader.readAllCosts());
    }

    @Nonnull
    public BigDecimal getBalance(@Nullable Collection<Cost> costs) {
        return calculator.getBalance(costs);
    }


}
