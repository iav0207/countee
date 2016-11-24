package ru.iav.takoe.countee.service.model;

import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;

import static ru.iav.takoe.countee.utils.ObjectUtils.defensiveCopy;
import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

/**
 * TODO extract balance calculation to model module
 */
public class BalanceCalculator {

    private static BalanceCalculator instance = new BalanceCalculator();

    private CostReader reader;

    private BalanceCalculator() {
        reader = CostReader.getInstance();
    }

    public static BalanceCalculator getInstance() {
        return instance;
    }

    @Nonnull
    public BigDecimal getCurrentBalance() {
        return getBalance(reader.readAllCosts());
    }

    @Nonnull
    public BigDecimal getBalance(@Nullable Collection<Cost> costs) {
        BigDecimal balance = BigDecimal.ZERO;
        for (Cost each : defensiveCopy(costs)) {
            balance = balance.subtract(amountOf(each));
        }
        return balance;
    }

    @Nonnull
    private BigDecimal amountOf(@Nullable Cost cost) {
        return isNull(cost) || isNull(cost.getAmount()) ? BigDecimal.ZERO : cost.getAmount();
    }

}
