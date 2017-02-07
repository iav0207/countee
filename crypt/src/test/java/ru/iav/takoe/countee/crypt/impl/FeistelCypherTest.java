package ru.iav.takoe.countee.crypt.impl;

import org.testng.annotations.Test;
import ru.iav.takoe.countee.crypt.vo.Block64;

import static org.testng.Assert.assertEquals;

/**
 * Created by takoe on 06.02.17.
 */
public class FeistelCypherTest {

    @Test
    public void testSubstitutionRounds() {
        Block64[] textBlocks = new Block64[] {new Block64(10, 10)};
        int[] key = new int[] {1, 2, 3, 4, 5, 6, 7, 8};

        Block64[] result = new FeistelCypher(textBlocks, key, true).executeSubstitutionLoops();
        assertEquals(result[0], new Block64(1882551425, 1656703846));
    }

}