package ru.iav.takoe.countee.service.model;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.vo.Cost;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

/**
 * Created by takoe on 21.11.16.
 */
public class BalanceCalculatorTest {

    @Mock
    private CostReader reader;

    @InjectMocks
    private BalanceCalculator calculator;

    @BeforeClass
    public void init() {
        initMocks(this);
    }

    @BeforeMethod
    public void reset() {
        Mockito.reset(reader);
    }

    @Test
    public void shouldReturnZeroIfInputIsNull() throws Exception {
        assertEquals(BigDecimal.ZERO, calculator.getBalance(null));
    }

    @Test
    public void shouldReturnZeroIfInputIsEmpty() throws Exception {
        assertEquals(BigDecimal.ZERO, calculator.getBalance(new ArrayList<Cost>()));
    }

    @Test(dataProvider = "getCosts", dataProviderClass = BalanceCalculatorTestData.class)
    public void shouldReturnNegativeAlgebraicSumOfGivenCosts(
            List<Cost> costs, BigDecimal expectedBalance) throws Exception {
        letReaderReturn(costs);
        assertEquals(calculator.getCurrentBalance(), expectedBalance);
    }

    @Test(dataProvider = "getCosts", dataProviderClass = BalanceCalculatorTestData.class)
    public void shouldCurrentBalanceBeEqualToBalanceCountedWithAllCosts(
            List<Cost> costs, BigDecimal expectedBalance) throws Exception {
        letReaderReturn(costs);
        assertEquals(calculator.getCurrentBalance(), calculator.getBalance(costs));
    }

    private void letReaderReturn(Collection<Cost> costs) {
        doReturn(costs).when(reader).readAllCosts();
    }

}