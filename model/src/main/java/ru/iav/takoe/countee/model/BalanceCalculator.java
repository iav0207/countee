package ru.iav.takoe.countee.model;

import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;

import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;
import static ru.iav.takoe.countee.utils.StreamUtils.getStream;

/**
 * Created by takoe on 30.11.16.
 */
public class BalanceCalculator {

    private static BalanceCalculator instance;

    private BalanceCalculator() {}

    public static BalanceCalculator getInstance() {
        if (isNull(instance)) {
            instance = new BalanceCalculator();
        }
        return instance;
    }

    @Nonnull
    public BigDecimal getBalance(@Nullable Collection<Cost> costs) {
        return getStream(costs)
                .map(this::amountOf)
                .reduce(BigDecimal.ZERO,
                        BigDecimal::subtract);
    }

    @Nonnull
    private BigDecimal amountOf(@Nullable Cost cost) {
        return isNull(cost) || isNull(cost.getAmount()) ? BigDecimal.ZERO : cost.getAmount();
    }

}
