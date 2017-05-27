package ru.iav.takoe.countee.model.utils;

import ru.iav.takoe.countee.vo.Cost;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

public class CounteeTestUtils {

    public static List<Cost> costList(Integer... ints) {
        List<Cost> list = new ArrayList<>();
        for (Integer each : ints) {
            list.add(cost(each));
        }
        return list;
    }

    public static List<Cost> costList(String... strings) {
        List<Cost> list = new ArrayList<>();
        for (String each : strings) {
            list.add(cost(each));
        }
        return list;
    }

    public static Cost cost(Integer amount) {
        Cost mock = mock(Cost.class);
        doReturn(isNull(amount) ? null : new BigDecimal(amount))
                .when(mock).getAmount();
        return mock;
    }

    public static Cost cost(String amount) {
        Cost mock = mock(Cost.class);
        doReturn(isNull(amount) ? null : new BigDecimal(amount))
                .when(mock).getAmount();
        return mock;
    }

    public static List<Cost> listOfCostsWithComments(String... comments) {
        List<Cost> list = new ArrayList<>();
        for (String each : comments) {
            list.add(costWithComment(each));
        }
        return list;
    }

    public static Cost costWithComment(String comment) {
        Cost mock = mock(Cost.class);
        doReturn(comment).when(mock).getComment();
        return mock;
    }
    
}
