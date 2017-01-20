package ru.iav.takoe.countee.model.map;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import ru.iav.takoe.countee.model.comparator.CostDateComparator;
import ru.iav.takoe.countee.utils.StreamUtils;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static ru.iav.takoe.countee.model.CostDateUtil.day;
import static ru.iav.takoe.countee.model.CostDateUtil.month;
import static ru.iav.takoe.countee.utils.StreamUtils.getStream;
import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

/**
 * Created by takoe on 16.11.16.
 */
public class DateCostMultimapBuilder {

    private static DateCostMultimapBuilder instance = new DateCostMultimapBuilder();

    private static final CostDateComparator costDateComparator = new CostDateComparator();

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
    public Multimap<DateTime, Cost> groupByDays(@Nullable List<Cost> costs) {
        Multimap<DateTime, Cost> multimap = LinkedListMultimap.create();
        StreamUtils.reverse(getStream(costs)).forEach(cost -> multimap.put(day(cost), cost));
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
                DateTimeComparator.getInstance(), costDateComparator);
        return buildMonthsMultimap(multimap, costs);
    }

    private static Multimap<DateTime, Cost> buildMonthsMultimap(@Nonnull Multimap<DateTime, Cost> multimap,
                                                                @Nullable List<Cost> costs) {
        StreamUtils.reverse(getStream(costs))
                .filter(cost -> !isNull(cost))
                .forEach(cost -> multimap.put(month(cost), cost));
        return multimap;
    }



}
