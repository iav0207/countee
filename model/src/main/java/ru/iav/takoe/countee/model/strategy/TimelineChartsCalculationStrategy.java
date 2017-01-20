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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.iav.takoe.countee.utils.ObjectUtils.defensiveCopy;
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
        return getAllDates()
                .map(DateTime::withTimeAtStartOfDay)
                .map(dateTime -> dateTime.withDayOfMonth(1))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private Stream<DateTime> getAllDates() {
        return costMultimap.keySet().stream();
    }

    @Nonnull
    static BigDecimal sum(@Nullable Collection<Cost> costs) {
        return defensiveCopy(costs).stream()
                .map(TimelineChartsCalculationStrategy::amountOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Nonnull
    private static BigDecimal amountOf(@Nullable Cost cost) {
        return isNull(cost) || isNull(cost.getAmount()) ? BigDecimal.ZERO : cost.getAmount();
    }

    static DateCostMultimapBuilder multimapBuilder() {
        return DateCostMultimapBuilder.getInstance();
    }

}
