package ru.iav.takoe.countee.model.strategy;

import org.joda.time.DateTime;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class FundsDailyStrategy extends TimelineChartsCalculationStrategy {

    public FundsDailyStrategy(@Nonnull List<Cost> costs) {
        super(costs);
    }

    @Override
    public Map<DateTime, Float> execute() {
        costMultimap = multimapBuilder().groupByDays(costs());
        if (costMultimap.isEmpty()) {
            return result;
        }
        BigDecimal funds = getBalance();
        TreeSet<DateTime> days = createDatesSet();

        for (DateTime eachDayBackwards = days.last();
             !eachDayBackwards.isBefore(days.first());
             eachDayBackwards = eachDayBackwards.minusDays(1)) {

            result.put(eachDayBackwards, funds.floatValue());

            for (Cost eachCostInThisDay : costMultimap.get(eachDayBackwards)) {
                funds = funds.add(eachCostInThisDay.getAmount());
            }
        }
        return result;
    }

}
