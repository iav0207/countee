package ru.iav.takoe.countee.service.model.strategy;

import com.google.common.collect.Multimap;
import org.joda.time.DateTime;
import ru.iav.takoe.countee.service.BalanceService;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by takoe on 16.11.16.
 */
abstract class ChartsDataCalculationStrategy {

    private final List<Cost> costs;

    Map<DateTime, Float> result;

    Multimap<DateTime, Cost> costMultimap;

    ChartsDataCalculationStrategy(@Nonnull List<Cost> costs) {
        this.costs = costs;
        result = new TreeMap<>();
    }

    public abstract Map<DateTime, Float> execute();

    List<Cost> costs() {
        return costs;
    }

    BigDecimal getBalance() {
        return BalanceService.getInstance().getBalance(costs);
    }

    static DateCostMultimapBuilder multimapBuilder() {
        return DateCostMultimapBuilder.getInstance();
    }

}
