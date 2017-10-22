package ru.iav.takoe.countee.vo.comparator;

import java.util.Comparator;
import java.util.Date;

import org.joda.time.DateTimeComparator;
import ru.iav.takoe.countee.vo.Cost;

import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

public class CostDateComparator implements Comparator<Cost> {

    @Override
    public int compare(Cost first, Cost second) {
        return DateTimeComparator.getInstance().compare(date(first), date(second));
    }

    private Date date(Cost cost) {
        if (isNull(cost)) {
            return null;
        }
        return cost.getTimestamp();
    }

}
