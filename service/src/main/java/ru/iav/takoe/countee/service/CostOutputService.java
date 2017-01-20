package ru.iav.takoe.countee.service;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.StringUtils;
import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.service.exception.NoSuchMonthException;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static ru.iav.takoe.countee.utils.ObjectUtils.defensiveCopy;
import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;
import static ru.iav.takoe.countee.utils.StreamUtils.getStream;

/**
 * Created by takoe on 16.08.16.
 */
public class CostOutputService {

    private static CostOutputService instance;

    private static final DecimalFormat decimalFormat = new DecimalFormat("#0.##");

    static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM");

    private static final String
            newLine = "\n",
            empty = StringUtils.EMPTY;

    private CostReader reader;

    private BalanceService balanceService;

    private MonthOutputService monthOutputService;

    public static CostOutputService getInstance() {
        if (isNull(instance)) {
            instance = new CostOutputService();
        }
        return instance;
    }

    private CostOutputService() {
        reader = CostReader.getInstance();
        balanceService = BalanceService.getInstance();
        monthOutputService = MonthOutputService.getInstance();
    }

    public String getCurrentBalanceOutput() {
        return toString(balanceService.getCurrentBalance());
    }

    public String getCurrentMonthOutput() {
        return toString(reader.readCostsForThisMonth());
    }

    public String getOutputForPrevMonth(int monthsAgo) throws NoSuchMonthException {
        return toString(monthOutputService.getCostsForPrevMonth(monthsAgo));
    }

    public int getMonthsCount() {
        return monthOutputService.getMonthsCount();
    }

    @Nonnull
    // TODO extract toString algorithm to a class
    private String toString(@Nullable Collection<Cost> costs) {
        if (defensiveCopy(costs).isEmpty()) {
            return empty;
        }
        Multimap<String, Cost> groupedCosts = groupByDates(costs);
        return getDateStrings(groupedCosts)
                .map((dateString) -> getStream(groupedCosts.get(dateString))
                        .map(this::toString)
                        .collect(joining(newLine, dateString + newLine, newLine)))
                .collect(joining(newLine, empty, newLine));
    }

    @Nonnull
    private Stream<String> getDateStrings(@Nonnull Multimap<String, Cost> groupedCosts) {
        return getStream(groupedCosts.keySet());
    }

    private Multimap<String, Cost> groupByDates(Collection<Cost> costs) {
        /* Map version
        return getStream(costs)
                .filter(cost -> !isNull(cost))
                .collect(groupingBy(cost -> toString(cost.getTimestamp())));*/
        Multimap<String, Cost> multimap = LinkedListMultimap.create();
        costs.forEach(cost -> multimap.put(toString(cost.getTimestamp()), cost));
        return multimap;
    }

    private String toString(@Nullable Date date) {
        return isNull(date) ? StringUtils.EMPTY : dateFormat.format(date);
    }

    private String toString(@Nonnull Cost cost) {
        return Stream.of(toString(cost.getAmount()), cost.getComment())
                .collect(joining(StringUtils.SPACE));
    }

    private String toString(@Nonnull BigDecimal bigDecimal) {
        return decimalFormat.format(bigDecimal);
    }

}
