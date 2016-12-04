package ru.iav.takoe.countee.service;

import org.joda.time.DateTime;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.model.map.DateCostMultimapBuilder;
import ru.iav.takoe.countee.service.exception.NoSuchMonthException;
import ru.iav.takoe.countee.vo.Cost;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static ru.iav.takoe.countee.utils.DateUtils.month;
import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomBoolean;

/**
 * Created by takoe on 04.12.16.
 */
public class MonthOutputServiceTest {

    @Mock
    private CostReader reader;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private DateCostMultimapBuilder multimapBuilder;

    @InjectMocks
    private MonthOutputService service;

    @BeforeClass
    public void init() {
        initMocks(this);
    }

    @BeforeMethod
    public void reset() {
        Mockito.reset(reader);
        service.invalidate();
    }

    @Test
    public void shouldNotFailIfReaderReturnsNull() throws Exception {
        letReaderReturn(null);
        service.getMonthsCount();
        service.getCostsForPrevMonth(0);
    }

    @Test
    public void shouldNotFailIfReaderReturnsEmptyList() throws Exception {
        letReaderReturn(new ArrayList<Cost>());
        service.getMonthsCount();
        service.getCostsForPrevMonth(0);
    }

    @Test(dataProvider = "spreadCount", dataProviderClass = MonthOutputServiceTestData.class)
    public void shouldCountMonthsSpreadForAllCostsReturnedByReader(List<Cost> allCosts, int expected)
            throws Exception {
        letReaderReturn(allCosts);
        assertEquals(service.getMonthsCount(), expected);
    }

    @Test(dataProvider = "specifiedMonth", dataProviderClass = MonthOutputServiceTestData.class)
    public void shouldReturnCostsOnlyForSpecifiedMonth(List<Cost> allCosts, DateTime month) throws Exception {
        letReaderReturn(allCosts);
        List<Cost> costsForPrevMonth = service.getCostsForPrevMonth(1);
        if (isNull(month)) {
            assertEquals(costsForPrevMonth.size(), 0);
        } else {
            for (Cost eachResultElement : costsForPrevMonth) {
                assertEquals(month(eachResultElement.getTimestamp()), month);
            }
        }
    }

    @Test(expectedExceptions = NoSuchMonthException.class,
            dataProvider = "spreadCount", dataProviderClass = MonthOutputServiceTestData.class)
    public void shouldThrowExceptionIfThereIsNoSuchMonth(List<Cost> allCosts, int spread) throws Exception {
        letReaderReturn(allCosts);
        service.getCostsForPrevMonth(plusMinus(spread + 1));
    }

    private int plusMinus(int value) {
        return getRandomBoolean() ? value : -value;
    }

    private void letReaderReturn(List<Cost> costs) {
        doReturn(costs).when(reader).readAllCosts();
    }

}