package ru.iav.takoe.countee.service;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
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

import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

/**
 * Created by takoe on 16.08.16.
 */
public class CostOutputService {

    private static CostOutputService instance;

    private static final DecimalFormat decimalFormat = new DecimalFormat("#0.##");

    static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM");

    private static final String outputFormat = "%s %s";

    private static final Character newLine = '\n';

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
    private String toString(@Nullable Collection<Cost> costs) {
        if (costs == null || costs.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Multimap<String, Cost> costMultimap = toMultimap(costs);
        for (String dateString : costMultimap.keySet()) {
            sb.append(dateString).append(newLine);
            for (Cost eachInThisDate : costMultimap.get(dateString)) {
                sb.append(toString(eachInThisDate)).append(newLine);
            }
            sb.append(newLine);
        }
        return sb.toString();
    }

    private Multimap<String, Cost> toMultimap(Iterable<Cost> costs) {
        Multimap<String, Cost> multimap = LinkedListMultimap.create();
        for (Cost each : costs) {
            multimap.put(toString(each.getTimestamp()), each);
        }
        return multimap;
    }

    private String toString(@Nonnull Date date) {
        return dateFormat.format(date);
    }

    private String toString(@Nonnull Cost cost) {
        return formatOutput(cost.getAmount(), cost.getComment());
    }

    private String formatOutput(@Nonnull BigDecimal amount, String comment) {
        return String.format(outputFormat, toString(amount), comment);
    }

    private String toString(@Nonnull BigDecimal bigDecimal) {
        return decimalFormat.format(bigDecimal);
    }

}
