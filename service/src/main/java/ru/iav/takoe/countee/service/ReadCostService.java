package ru.iav.takoe.countee.service;

import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by takoe on 16.08.16.
 */
public class ReadCostService {

    private static ReadCostService instance = new ReadCostService();

    private static final DecimalFormat decimalFormat = new DecimalFormat("#0.##");

    private static final String outputFormat = "%s %s";

    private CostReader reader;

    public static ReadCostService getInstance() {
        return instance;
    }

    private ReadCostService() {
        reader = CostReader.getInstance();
    }

    public String getCurrentMonthOutput() {
        List<Cost> costs = reader.readCostsForThisMonth();
        return costs == null ? "" : toString(costs);
    }

    private String toString(@Nonnull Iterable<Cost> costs) {
        StringBuilder sb = new StringBuilder();
        for (Cost each : costs) {
            sb.append(toString(each)).append('\n');
        }
        return sb.toString();
    }

    private String toString(@Nonnull Cost cost) {
        return formatOutput(cost.getAmount(), cost.getComment());
    }

    private String formatOutput(BigDecimal amount, String comment) {
        return String.format(outputFormat, format(amount), comment);
    }

    private String format(BigDecimal bigDecimal) {
        return decimalFormat.format(bigDecimal);
    }

}
