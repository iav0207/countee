package ru.iav.takoe.statbm.vo;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;
import static ru.iav.takoe.statbm.utils.TestUtils.getRandomBigDecimal;
import static ru.iav.takoe.statbm.utils.TestUtils.getRandomString;

/**
 * Created by takoe on 23.07.16.
 */
public class CostFactoryTest {

    @Mock
    private CostValidator validator;

    @InjectMocks
    private CostFactory costFactory;

    @BeforeClass
    public void init() {
        initMocks(this);
    }

    @Test
    public void shouldReturnSpecifiedCostInstanceWhenInputIsValid() throws Exception {
        BigDecimal amount = getRandomBigDecimal();
        String comment = getRandomString();

        Cost result = getCost(amount, comment);

        assertNotNull(result.getUuid());
        assertNotNull(result.getTimestamp());
        assertEquals(result.getAmount(), amount);
        assertEquals(result.getComment(), comment);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionIfInputArgsAreInvalid() throws Exception {
        doThrow(new IllegalArgumentException()).when(validator).validate(any(BigDecimal.class));
        doThrow(new IllegalArgumentException()).when(validator).validate(anyString());

        getCost(getRandomBigDecimal(), getRandomString());
    }

    private Cost getCost(BigDecimal amount, String comment) {
        return costFactory.create(amount, comment);
    }

}