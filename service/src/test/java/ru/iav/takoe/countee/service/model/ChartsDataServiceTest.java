package ru.iav.takoe.countee.service.model;

import org.joda.time.DateTime;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.service.BalanceService;
import ru.iav.takoe.countee.service.ChartsDataService;
import ru.iav.takoe.countee.model.comparator.CostDateComparator;
import ru.iav.takoe.countee.vo.Cost;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static java.lang.Math.abs;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static ru.iav.takoe.countee.utils.DateUtils.day;

public class ChartsDataServiceTest {

    private static final CostDateComparator comparator = new CostDateComparator();

    @Mock
    private CostReader reader;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    BalanceService balanceService;

    @InjectMocks
    private ChartsDataService service;

    private Map<DateTime, Float> result;

    @BeforeClass
    public void init() {
        initMocks(this);
    }

    @Test
    public void shouldReturnEmptyCollectionIfReaderReturnedNull() throws Exception {
        doReturn(null).when(reader).readAllCosts();
        callService();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnEmptyCollectionIfReaderReturnedEmptyData() throws Exception {
        doReturn(new ArrayList<Cost>()).when(reader).readAllCosts();
        callService();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test(invocationCount = 5,
            dataProvider = "getCostsForSpacedDates", dataProviderClass = ChartsDataServiceTestData.class)
    public void shouldContainEachDayInPeriod(List<Cost> costs, Integer daysGap) throws Exception {
        doReturn(costs).when(reader).readAllCosts();
        callService();

        assertEquals(result.size(), daysGap + 1);
        for (Cost eachCost : costs) {
            assertTrue(result.containsValue(eachCost.getAmount().floatValue()));
        }
        DateTime from = getMinDate(costs);
        DateTime to = getMaxDate(costs);
        for (DateTime day = from; !day.isAfter(to); day = day.plusDays(1)) {
            assertTrue(result.containsKey(day));
        }
    }

    @Test(dataProvider = "getRandomCosts", dataProviderClass = ChartsDataServiceTestData.class)
    public void shouldCurrentBalanceCorrespondToTheLastDateFundsValue(List<Cost> costs, BigDecimal expectedBalance)
            throws Exception {
        doReturn(costs).when(reader).readAllCosts();
        callService();

        if (costs.size() > 0) {
            BigDecimal actualBalance = ChartsDataServiceTestData.bigD(result.get(getMaxDateTime(result.keySet())));
            assertTrue(areEqual(actualBalance, expectedBalance));
        }
    }

    private static boolean areEqual(BigDecimal one, BigDecimal two) {
        float epsilon = 1e-8f;
        return abs(one.subtract(two).floatValue()) < epsilon;
    }

    private static DateTime getMaxDateTime(Collection<DateTime> dates) {
        return new TreeSet<>(dates).last();
    }

    private static DateTime getMaxDate(Collection<Cost> costs) {
        return getDay(sort(costs).last());
    }

    private static DateTime getMinDate(Collection<Cost> costs) {
        return getDay(sort(costs).first());
    }

    private static TreeSet<Cost> sort(Collection<Cost> costs) {
        TreeSet<Cost> sortedCosts = new TreeSet<>(comparator);
        sortedCosts.addAll(costs);
        return sortedCosts;
    }

    private static DateTime getDay(Cost cost) {
        return day(cost.getTimestamp());
    }

    private void callService() {
        result = service.getFundsDailyData();
    }

}
