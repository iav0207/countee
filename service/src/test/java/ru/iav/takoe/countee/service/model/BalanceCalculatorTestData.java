package ru.iav.takoe.countee.service.model;

import org.testng.annotations.DataProvider;
import ru.iav.takoe.countee.vo.Cost;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

/**
 * Created by takoe on 21.11.16.
 */
public class BalanceCalculatorTestData {

    @DataProvider(name = "getCosts")
    public static Object[][] getCosts() {
        return new Object[][] {
                {costList(0, 0, 0, 0),          bd(0)},
                {costList(1, 2, 3, 900),        bd(-906)},
                {costList(-1, -100, 5, 0),      bd(96)},
                {costList(-1, -1, -1, null),    bd(3)},
                {costList("0.09", "0.9", "-1"), bd("0.01")}
        };
    }

    private static List<Cost> costList(Integer... ints) {
        List<Cost> list = new ArrayList<>();
        for (Integer each : ints) {
            list.add(cost(each));
        }
        return list;
    }

    private static List<Cost> costList(String... strings) {
        List<Cost> list = new ArrayList<>();
        for (String each : strings) {
            list.add(cost(each));
        }
        return list;
    }

    private static Cost cost(Integer amount) {
        Cost mock = mock(Cost.class);
        doReturn(isNull(amount) ? null : new BigDecimal(amount))
                .when(mock).getAmount();
        return mock;
    }

    private static Cost cost(String amount) {
        Cost mock = mock(Cost.class);
        doReturn(isNull(amount) ? null : new BigDecimal(amount))
                .when(mock).getAmount();
        return mock;
    }

    private static BigDecimal bd(Integer amount) {
        return new BigDecimal(amount);
    }

    private static BigDecimal bd(String amount) {
        return new BigDecimal(amount);
    }

}
