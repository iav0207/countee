package ru.iav.takoe.countee.model.strategy;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import org.joda.time.DateTime;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import java.util.List;

import static ru.iav.takoe.countee.model.CostDateUtil.day;
import static ru.iav.takoe.countee.model.CostDateUtil.month;

/**
 * Created by takoe on 16.11.16.
 */
class DateCostMultimapBuilder {

    private static DateCostMultimapBuilder instance = new DateCostMultimapBuilder();

    private DateCostMultimapBuilder() {}

    public static DateCostMultimapBuilder getInstance() {
        return instance;
    }

    /**
     * Get a multimap of costs grouped for each day.
     *
     * @return A {@link LinkedListMultimap}, <b>filled backwards</b>,
     * i.e. from current day to the first day of statistics.
     */
    @Nonnull
    Multimap<DateTime, Cost> groupByDays(List<Cost> costs) {
        Multimap<DateTime, Cost> multimap = LinkedListMultimap.create();
        for (int i = costs.size() - 1; i >= 0; i--) {
            Cost cost = costs.get(i);
            multimap.put(day(cost), cost);
        }
        return multimap;
    }

    /**
     * Get a multimap of costs grouped for each month.
     *
     * @return A {@link LinkedListMultimap}, <b>filled backwards</b>,
     * i.e. from current month to the first month of statistics.
     */
    @Nonnull
    Multimap<DateTime, Cost> groupByMonths(List<Cost> costs) {
        Multimap<DateTime, Cost> multimap = LinkedListMultimap.create();
        for (int i = costs.size() - 1; i >= 0; i--) {
            Cost cost = costs.get(i);
            multimap.put(month(cost), cost);
        }
        return multimap;
    }

}
