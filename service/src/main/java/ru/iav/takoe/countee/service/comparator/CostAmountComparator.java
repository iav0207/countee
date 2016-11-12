package ru.iav.takoe.countee.service.comparator;

import ru.iav.takoe.countee.vo.Cost;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Created by takoe on 12.11.16.
 */
public class CostAmountComparator implements Comparator<Cost> {

    @Override
    public int compare(Cost first, Cost second) {
        return amount(first).compareTo(amount(second));
    }

    private BigDecimal amount(Cost cost) {
        return cost.getAmount();
    }

}
