package ru.iav.takoe.countee.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.da.Reader;
import ru.iav.takoe.countee.model.BalanceCalculator;
import ru.iav.takoe.countee.vo.Cost;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static ru.iav.takoe.countee.test.CounteeTestUtils.getListOfRandomCosts;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomBigDecimal;

public class BalanceServiceTest {

    @Mock
    private Reader<Cost> reader;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private BalanceCalculator calculator;

    @InjectMocks
    private BalanceService balanceService;

    @BeforeClass
    public void init() {
        initMocks(this);
    }

    @BeforeMethod
    public void reset() {
        Mockito.reset(reader, calculator);
    }

    @Test
    public void shouldReturnZeroIfInputIsNull() throws Exception {
        assertEquals(BigDecimal.ZERO, balanceService.getBalance(null));
    }

    @Test
    public void shouldReturnZeroIfInputIsEmpty() throws Exception {
        assertEquals(BigDecimal.ZERO, balanceService.getBalance(new ArrayList<Cost>()));
    }

    @Test
    public void shouldCurrentBalanceBeEqualToBalanceCountedWithAllCosts() throws Exception {
        List<Cost> costs = getListOfRandomCosts(10);
        letReaderReturn(costs);
        assertEquals(balanceService.getCurrentBalance(), balanceService.getBalance(costs));
    }

    @Test
    public void shouldReturnWhatCalculatorReturned() throws Exception {
        BigDecimal expectedResult = getRandomBigDecimal();
        letCalculatorReturn(expectedResult);
        BigDecimal actualResult = balanceService.getBalance(getListOfRandomCosts(5));
        assertEquals(actualResult, expectedResult);
    }

    private void letReaderReturn(Collection<Cost> costs) {
        doReturn(costs).when(reader).readAll();
    }

    @SuppressWarnings("unchecked")
    private void letCalculatorReturn(BigDecimal result) {
        doReturn(result).when(calculator).getBalance(any(Collection.class));
    }

}
