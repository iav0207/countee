package ru.iav.takoe.countee.model;

import org.joda.time.DateTime;
import ru.iav.takoe.countee.utils.DateUtils;
import ru.iav.takoe.countee.vo.Cost;

/**
 * Created by takoe on 16.11.16.
 */
public class CostDateUtil {

    public static DateTime day(Cost cost) {
        return DateUtils.day(cost.getTimestamp());
    }

    public static DateTime month(Cost cost) {
        return DateUtils.month(cost.getTimestamp());
    }

}
