package ru.iav.takoe.countee.model;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.vo.Cost;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static ru.iav.takoe.countee.model.utils.CounteeTestUtils.costList;

/**
 * Created by takoe on 30.11.16.
 */
public class BalanceCalculatorTest {

    private BalanceCalculator calculator = BalanceCalculator.getInstance();

    @Test
    public void shouldReturnZeroIfInputIsNull() throws Exception {
        assertEquals(BigDecimal.ZERO, calculator.getBalance(null));
    }

    @Test
    public void shouldReturnZeroIfInputIsEmpty() throws Exception {
        assertEquals(BigDecimal.ZERO, calculator.getBalance(new ArrayList<Cost>()));
    }

    @Test(dataProvider = "getCosts")
    public void shouldReturnNegativeAlgebraicSumOfGivenCosts(
            List<Cost> costs, BigDecimal expectedBalance) throws Exception {
        assertEquals(calculator.getBalance(costs), expectedBalance);
    }

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

    private static BigDecimal bd(Integer amount) {
        return new BigDecimal(amount);
    }

    private static BigDecimal bd(String amount) {
        return new BigDecimal(amount);
    }

}