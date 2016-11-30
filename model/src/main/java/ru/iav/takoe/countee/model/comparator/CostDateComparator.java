package ru.iav.takoe.countee.model.comparator;

import ru.iav.takoe.countee.vo.Cost;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by takoe on 12.11.16.
 */
public class CostDateComparator implements Comparator<Cost> {

    @Override
    public int compare(Cost first, Cost second) {
        if (date(first).before(date(second))) {
            return -1;
        } else if (date(first).after(date(second))) {
            return 1;
        }
        return 0;
    }

    private Date date(Cost cost) {
        return cost.getTimestamp();
    }

}
