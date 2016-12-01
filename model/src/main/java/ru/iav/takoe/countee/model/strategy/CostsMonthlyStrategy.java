package ru.iav.takoe.countee.model.strategy;

import org.joda.time.DateTime;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * TODO cover with tests
 */
public class CostsMonthlyStrategy extends TimelineChartsCalculationStrategy {

    public CostsMonthlyStrategy(@Nonnull List<Cost> costs) {
        super(costs);
    }

    @Override
    public Map<DateTime, Float> execute() {
        costMultimap = multimapBuilder().groupByMonths(costs());
        if (costMultimap.isEmpty()) {
            return result;
        }
        TreeSet<DateTime> months = getMonthsSorted();

        for (DateTime eachMonthBackwards = months.last();
             !eachMonthBackwards.isBefore(months.first());
             eachMonthBackwards = eachMonthBackwards.minusMonths(1)) {

            BigDecimal sum = sum(costMultimap.get(eachMonthBackwards));
            result.put(eachMonthBackwards, sum.floatValue());
        }
        return result;
    }

}
