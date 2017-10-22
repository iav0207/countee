package ru.iav.takoe.countee.vo.comparator;

import java.util.Comparator;
import java.util.Objects;

import javax.annotation.ParametersAreNonnullByDefault;

import ru.iav.takoe.countee.vo.Cost;

/**
 * The goal of this comparator is to return zero
 * if and only if the two instances are exactly equal.
 */
@ParametersAreNonnullByDefault
public class CostIntegralComparator implements Comparator<Cost> {

    private final CostDateComparator dateComparator = new CostDateComparator();

    private final CostAmountComparator amountComparator = new CostAmountComparator();

    @Override
    public int compare(Cost first, Cost second) {
        if (Objects.equals(first, second)) {
            return 0;
        }
        int dateCmp = dateComparator.compare(first, second);
        if (dateCmp != 0) {
            return dateCmp;
        }
        int amountCmp = amountComparator.compare(first, second);
        if (amountCmp != 0) {
            return amountCmp;
        }
        return Integer.compare(Objects.hashCode(first), Objects.hashCode(second));
    }

}
