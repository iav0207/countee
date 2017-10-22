package ru.iav.takoe.countee.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.testng.annotations.DataProvider;
import ru.iav.takoe.countee.vo.Cost;

import static java.math.BigDecimal.ZERO;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static ru.iav.takoe.countee.utils.DateUtils.today;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomInteger;

public class ChartsDataServiceTestData {

    @DataProvider(name = "getCostsForSpacedDates")
    public static Object[][] getCostsForSpacedDates() {
        Integer daysGap = getRandomInteger() % 100;
        return new Object[][] {{ getCosts(daysGap), daysGap }};
    }

    @DataProvider(name = "getRandomCosts")
    public static Object[][] getRandomCosts() {
        return new Object[][]{
                {costs(
                        bigD(100f),
                        bigD(10f),
                        bigD(1f),
                        bigD(0.1f)
                ), bigD(-111.1f)},
                {costs(
                        bigD(0)
                ), ZERO},
                {costs(
                        bigD(-23.67f),
                        bigD(3f)
                ), bigD(20.67f)},
                {new ArrayList<>(), ZERO}
        };
    }

    static BigDecimal bigD(float value) {
        return new BigDecimal(Float.toString(value));
    }

    private static List<Cost> costs(BigDecimal... amounts) {
        List<Cost> costs = new ArrayList<>();
        for (BigDecimal amount : amounts) {
            costs.add(cost(amount));
        }
        return costs;
    }

    private static List<Cost> getCosts(Integer daysGap) {
        List<Cost> costs = new ArrayList<>();
        costs.add(cost(today()));
        costs.add(cost(today().plusDays(daysGap)));
        return costs;
    }

    private static Cost cost(DateTime day) {
        Cost mock = mock(Cost.class);
        doReturn(day.toDate()).when(mock).getTimestamp();
        doReturn(ZERO).when(mock).getAmount();
        return mock;
    }

    private static Cost cost(BigDecimal amount) {
        Cost mock = mock(Cost.class);
        doReturn(randomDate()).when(mock).getTimestamp();
        doReturn(amount).when(mock).getAmount();
        return mock;
    }

    private static Date randomDate() {
        return today().minusDays(getRandomInteger() % 500).toDate();
    }

}
