package ru.iav.takoe.countee.crypt.impl;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by takoe on 06.02.17.
 */
public class FeistelFunctionTest {

    private FeistelFunction function = new FeistelFunction();

    @Test
    public void test() throws Exception {
        assertEquals(1339198618, function.apply(238475, 1293873));
    }

}