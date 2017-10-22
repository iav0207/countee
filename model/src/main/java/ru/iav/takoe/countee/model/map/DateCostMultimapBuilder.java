package ru.iav.takoe.countee.model.map;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import ru.iav.takoe.countee.vo.Cost;
import ru.iav.takoe.countee.vo.comparator.CostIntegralComparator;

import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;
import static ru.iav.takoe.countee.utils.ObjectUtils.safeList;
import static ru.iav.takoe.countee.vo.util.CostDateUtil.day;
import static ru.iav.takoe.countee.vo.util.CostDateUtil.month;

public class DateCostMultimapBuilder {

    /**
     * Get a multimap of costs grouped for each day.
     *
     * @return A {@link LinkedListMultimap}, <b>filled backwards</b>,
     * i.e. from current day to the first day of statistics.
     */
    @Nonnull
    public Multimap<DateTime, Cost> groupByDays(List<Cost> costs) {
        Multimap<DateTime, Cost> multimap = LinkedListMultimap.create();
        if (safeList(costs).isEmpty()) {
            return multimap;
        }
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
    public Multimap<DateTime, Cost> groupByMonths(List<Cost> costs) {
        Multimap<DateTime, Cost> multimap = LinkedListMultimap.create();
        return buildMonthsMultimap(multimap, costs);
    }

    /**
     * Get a multimap of costs grouped for each month, sorted ascending.
     *
     * @return A {@link TreeMultimap}.
     */
    @Nonnull
    public Multimap<DateTime, Cost> groupByMonthsSortedAsc(List<Cost> costs) {
        Multimap<DateTime, Cost> multimap = TreeMultimap.create(
                DateTimeComparator.getInstance(), new CostIntegralComparator());
        return buildMonthsMultimap(multimap, costs);
    }

    private Multimap<DateTime, Cost> buildMonthsMultimap(Multimap<DateTime, Cost> multimap, List<Cost> costs) {
        if (safeList(costs).isEmpty()) {
            return multimap;
        }
        for (int i = costs.size() - 1; i >= 0; i--) {
            Cost cost = costs.get(i);
            if (!isNull(cost)) {
                multimap.put(month(cost), cost);
            }
        }
        return multimap;
    }



}
