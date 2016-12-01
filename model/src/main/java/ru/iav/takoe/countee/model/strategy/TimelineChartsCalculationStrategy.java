package ru.iav.takoe.countee.model.strategy;

import com.google.common.collect.Multimap;
import org.joda.time.DateTime;
import ru.iav.takoe.countee.model.BalanceCalculator;
import ru.iav.takoe.countee.model.map.DateCostMultimapBuilder;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import static ru.iav.takoe.countee.utils.ObjectUtils.isEmpty;
import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

/**
 * Created by takoe on 16.11.16.
 */
abstract class TimelineChartsCalculationStrategy {

    private final List<Cost> costs;

    Map<DateTime, Float> result;

    Multimap<DateTime, Cost> costMultimap;

    TimelineChartsCalculationStrategy(@Nonnull List<Cost> costs) {
        this.costs = costs;
        result = new TreeMap<>();
    }

    public abstract Map<DateTime, Float> execute();

    List<Cost> costs() {
        return costs;
    }

    BigDecimal getBalance() {
        return BalanceCalculator.getInstance().getBalance(costs);
    }

    @Nonnull
    TreeSet<DateTime> createDatesSet() {
        return new TreeSet<>(costMultimap.keySet());
    }

    @Nonnull
    TreeSet<DateTime> getMonthsSorted() {
        TreeSet<DateTime> set = new TreeSet<>();
        for (DateTime each : costMultimap.keySet()) {
            set.add(each.withTimeAtStartOfDay().withDayOfMonth(1));
        }
        return set;
    }

    @Nonnull
    static BigDecimal sum(@Nullable Collection<Cost> costs) {
        BigDecimal sum = BigDecimal.ZERO;
        if (!isEmpty(costs)) {
            for (Cost each : costs) {
                sum = sum.add(amountOf(each));
            }
        }
        return sum;
    }

    @Nonnull
    private static BigDecimal amountOf(@Nullable Cost cost) {
        return isNull(cost) || isNull(cost.getAmount()) ? BigDecimal.ZERO : cost.getAmount();
    }

    static DateCostMultimapBuilder multimapBuilder() {
        return DateCostMultimapBuilder.getInstance();
    }

}
