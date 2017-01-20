package ru.iav.takoe.countee.service;

import com.google.common.collect.Multimap;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.Months;
import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.da.Invalidable;
import ru.iav.takoe.countee.model.map.DateCostMultimapBuilder;
import ru.iav.takoe.countee.service.exception.NoSuchMonthException;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Stream;

import static ru.iav.takoe.countee.utils.DateUtils.month;
import static ru.iav.takoe.countee.utils.DateUtils.now;
import static ru.iav.takoe.countee.utils.ObjectUtils.defensiveCopy;
import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

/**
 * Created by takoe on 04.12.16.
 */
class MonthOutputService implements Invalidable {

    private static MonthOutputService instance;

    private static final DateTimeComparator dateComparator = DateTimeComparator.getInstance();

    private DateCostMultimapBuilder multimapBuilder;

    private CostReader reader;

    private Multimap<DateTime, Cost> multimap;

    private DateTime minMonth;

    private DateTime maxMonth;

    private int monthsSpread;

    private MonthOutputService() {
        reader = CostReader.getInstance();
        multimapBuilder = DateCostMultimapBuilder.getInstance();
    }

    public static MonthOutputService getInstance() {
        if (isNull(instance)) {
            instance = new MonthOutputService();
        }
        return instance;
    }

    @Override
    public void invalidate() {
        multimap = null;
    }

    int getMonthsCount() {
        ensureCalculated();
        return Math.abs(Months.monthsBetween(minMonth, maxMonth).getMonths());
    }

    @Nonnull
    List<Cost> getCostsForPrevMonth(int monthsBefore) {
        ensureCalculated();
        if (monthsBefore < 0 || monthsBefore > monthsSpread) {
            throw new NoSuchMonthException();
        }
        return defensiveCopy(multimap.get(maxMonth.minusMonths(monthsBefore)));
    }

    private void ensureCalculated() {
        if (!calculated()) {
            calculate();
        }
    }

    private boolean calculated() {
        return !isNull(multimap);
    }

    private void calculate() {
        multimap = multimapBuilder.groupByMonthsSortedAsc(reader.readAllCosts());
        minMonth = getMinMonth();
        maxMonth = getMaxMonth();
        monthsSpread = getMonthsCount();
    }

    private DateTime getMinMonth() {
        return getAllDates().min(dateComparator)
                .orElseGet(() -> month(now()));
    }

    private DateTime getMaxMonth() {
        return getAllDates().max(dateComparator)
                .orElseGet(() -> month(now()));
    }

    private Stream<DateTime> getAllDates() {
        return multimap.keySet().stream();
    }

}
