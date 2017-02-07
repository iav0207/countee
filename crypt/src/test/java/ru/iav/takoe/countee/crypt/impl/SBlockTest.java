package ru.iav.takoe.countee.crypt.impl;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by takoe on 06.02.17.
 */
public class SBlockTest {

    private SBlock sBlock = new SBlock();

    @Test(dataProvider = "intsForSBlock")
    public void testSBlock(int given, int expected) {
        assertEquals(sBlock.substitute(given), expected);
    }

    @DataProvider(name = "intsForSBlock")
    public static Object[][] intsForSBlock() {
        return new Object[][] {
                {0,     -1813073125},
                {1,     -1813073126},
                {2,     -1813073121},
                {3,     -1813073131},
                {4,     -1813073136},
                {5,     -1813073124},
                {6,     -1813073122},
                {7,     -1813073128},
                {8,     -1813073130},
                {9,     -1813073134}
        };
    }

}