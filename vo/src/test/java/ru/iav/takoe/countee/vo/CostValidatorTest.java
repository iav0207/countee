package ru.iav.takoe.countee.vo;

import java.math.BigDecimal;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ru.iav.takoe.countee.utils.TestUtils.getRandomBigDecimal;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

public class CostValidatorTest {

    private CostValidator validator;

    @BeforeClass
    public void init() {
        validator = new CostValidator();
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
