package ru.iav.takoe.countee.service.model;

import com.google.common.collect.Multimap;
import org.joda.time.DateTime;
import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.service.model.strategy.DateCostMultimapBuilder;
import ru.iav.takoe.countee.vo.Cost;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import static ru.iav.takoe.countee.utils.ObjectUtils.safeList;

/**
 * Created by takoe on 12.11.16.
 */
public class FundsDataService {

    private static FundsDataService instance = new FundsDataService();

    private CostReader reader;

    private BalanceCalculator balanceCalculator;

    private DateCostMultimapBuilder multimapBuilder;

    private FundsDataService() {
        reader = CostReader.getInstance();
        balanceCalculator = BalanceCalculator.getInstance();
        multimapBuilder = DateCostMultimapBuilder.getInstance();
    }

    public static FundsDataService getInstance() {
        return instance;
    }

    public Map<DateTime, Float> getFundsDailyData() {
        Map<DateTime, Float> data = new TreeMap<>();
        Multimap<DateTime, Cost> costs = multimapBuilder.groupByDays(getAllCosts());
        if (costs.isEmpty()) {
            return data;
        }
        BigDecimal funds = balanceCalculator.getBalance(costs.values());
        TreeSet<DateTime> days = new TreeSet<>(costs.keySet());

        for (DateTime eachDayBackwards = days.last();
             !eachDayBackwards.isBefore(days.first());
             eachDayBackwards = eachDayBackwards.minusDays(1)) {

            data.put(eachDayBackwards, funds.floatValue());

            for (Cost eachCostInThisDay : costs.get(eachDayBackwards)) {
                funds = funds.add(eachCostInThisDay.getAmount());
            }
        }
        return data;
    }

    // TODO refactor (extract, strategy/visitor), cover with tests
    public Map<DateTime, Float> getFundsMonthlyData() {
        Map<DateTime, Float> data = new TreeMap<>();
        Multimap<DateTime, Cost> costs = multimapBuilder.groupByMonths(getAllCosts());
        if (costs.isEmpty()) {
            return data;
        }
        BigDecimal funds = balanceCalculator.getBalance(costs.values());
        TreeSet<DateTime> months = getMonthsSorted(costs.keySet());

        for (DateTime eachMonthBackwards = months.last();
             !eachMonthBackwards.isBefore(months.first());
             eachMonthBackwards = eachMonthBackwards.minusMonths(1)) {

            float monthAverage = 0f;
            Collection<Cost> costsInThisMonth = costs.get(eachMonthBackwards);
            for (Cost eachCostInThisMonth : costsInThisMonth) {
                BigDecimal costAmount = eachCostInThisMonth.getAmount();
                funds = funds.add(costAmount);
                monthAverage = monthAverage + (toFloat(funds) / costsInThisMonth.size());
            }
            data.put(eachMonthBackwards, monthAverage);
        }
        return data;
    }

    private static float toFloat(BigDecimal bd) {
        return bd.floatValue();
    }

    private TreeSet<DateTime> getMonthsSorted(Collection<DateTime> dates) {
        TreeSet<DateTime> set = new TreeSet<>();
        for (DateTime each : dates) {
            set.add(each.withTimeAtStartOfDay().withDayOfMonth(1));
        }
        return set;
    }

    private List<Cost> getAllCosts() {
        return safeList(reader.readAllCosts());
    }

}
