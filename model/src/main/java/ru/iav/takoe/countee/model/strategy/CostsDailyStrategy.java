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
public class CostsDailyStrategy extends TimelineChartsCalculationStrategy {

    public CostsDailyStrategy(@Nonnull List<Cost> costs) {
        super(costs);
    }

    @Override
    public Map<DateTime, Float> execute() {
        costMultimap = multimapBuilder().groupByDays(costs());
        if (costMultimap.isEmpty()) {
            return result;
        }
        TreeSet<DateTime> days = createDatesSet();

        for (DateTime eachDayBackwards = days.last();
             !eachDayBackwards.isBefore(days.first());
             eachDayBackwards = eachDayBackwards.minusDays(1)) {

            BigDecimal dailySum = sum(costMultimap.get(eachDayBackwards));
            result.put(eachDayBackwards, dailySum.floatValue());
        }
        return result;
    }

}
