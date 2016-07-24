package ru.iav.takoe.statbm.vo;

import net.bytebuddy.asm.Advice;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;
import static ru.iav.takoe.statbm.utils.TestUtils.getRandomBigDecimal;
import static ru.iav.takoe.statbm.utils.TestUtils.getRandomString;

/**
 * Created by takoe on 24.07.16.
 */
public class CostValidatorTest {

    private CostValidator validator;

    @BeforeClass
    public void init() {
        validator = CostValidator.getInstance();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionIfAmountIsNull() throws Exception {
        BigDecimal amount = null;
        validator.validate(amount);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionIfAmountIsZero() throws Exception {
        validator.validate(BigDecimal.ZERO);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionIfCommentIsNull() throws Exception {
        String comment = null;
        validator.validate(comment);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionIfCommentIsEmpty() throws Exception {
        validator.validate("");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionIfCommentContainsOnlyWhitespaces() throws Exception {
        validator.validate("         ");
    }

    @Test
    public void shouldBeSilentIfEverythingIsOk() throws Exception {
        validator.validate(getRandomBigDecimal());
        validator.validate(getRandomString());
    }

}