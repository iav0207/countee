package ru.iav.takoe.countee.vo.util;

import javax.annotation.Nullable;

import org.joda.time.DateTime;
import ru.iav.takoe.countee.utils.DateUtils;
import ru.iav.takoe.countee.vo.Cost;

import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

public class CostDateUtil {

    @Nullable
    public static DateTime day(@Nullable Cost cost) {
        return isNull(cost) ? null : DateUtils.day(cost.getTimestamp());
    }

    @Nullable
    public static DateTime month(@Nullable Cost cost) {
        return isNull(cost) ? null : DateUtils.month(cost.getTimestamp());
    }

}
