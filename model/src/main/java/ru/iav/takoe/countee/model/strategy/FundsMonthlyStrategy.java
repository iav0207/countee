package ru.iav.takoe.countee.model.strategy;

import org.joda.time.DateTime;
import ru.iav.takoe.countee.vo.Cost;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by takoe on 16.11.16.
 */
public class FundsMonthlyStrategy extends ChartsDataCalculationStrategy {

    public FundsMonthlyStrategy(List<Cost> costs) {
        super(costs);
    }

    // TODO  cover with tests
    @Override
    public Map<DateTime, Float> execute() {
        costMultimap = multimapBuilder().groupByMonths(costs());
        if (costMultimap.isEmpty()) {
            return result;
        }
        BigDecimal funds = getBalance();
        TreeSet<DateTime> months = getMonthsSorted(costMultimap.keySet());

        for (DateTime eachMonthBackwards = months.last();
             !eachMonthBackwards.isBefore(months.first());
             eachMonthBackwards = eachMonthBackwards.minusMonths(1)) {

            float monthAverage = 0f;
            Collection<Cost> costsInThisMonth = costMultimap.get(eachMonthBackwards);
            for (Cost eachCostInThisMonth : costsInThisMonth) {
                BigDecimal costAmount = eachCostInThisMonth.getAmount();
                funds = funds.add(costAmount);
                monthAverage += funds.floatValue() / costsInThisMonth.size();
            }
            result.put(eachMonthBackwards, monthAverage);
        }
        return result;
    }

    private TreeSet<DateTime> getMonthsSorted(Collection<DateTime> dates) {
        TreeSet<DateTime> set = new TreeSet<>();
        for (DateTime each : dates) {
            set.add(each.withTimeAtStartOfDay().withDayOfMonth(1));
        }
        return set;
    }

}
