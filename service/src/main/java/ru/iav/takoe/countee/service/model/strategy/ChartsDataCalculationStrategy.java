package ru.iav.takoe.countee.service.model.strategy;

import com.google.common.collect.Multimap;
import org.joda.time.DateTime;
import ru.iav.takoe.countee.service.model.BalanceCalculator;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by takoe on 16.11.16.
 */
public abstract class ChartsDataCalculationStrategy {

    private final List<Cost> costs;

    protected Map<DateTime, Float> result;

    protected Multimap<DateTime, Cost> costMultimap;

    public ChartsDataCalculationStrategy(@Nonnull List<Cost> costs) {
        this.costs = costs;
        result = new TreeMap<>();
    }

    public abstract Map<DateTime, Float> execute();

    protected List<Cost> costs() {
        return costs;
    }

    protected BigDecimal getBalance() {
        return BalanceCalculator.getInstance().getBalance(costs);
    }

    protected static DateCostMultimapBuilder multimapBuilder() {
        return DateCostMultimapBuilder.getInstance();
    }

}
