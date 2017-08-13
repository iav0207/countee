package ru.iav.takoe.countee.test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import ru.iav.takoe.countee.vo.Cost;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomBigDecimal;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomDateOfLastYear;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

public class CounteeTestUtils {

    public static File createFile(String key) throws IOException {
        File testFile = new File("io/test" + key);
        Files.deleteIfExists(testFile.toPath());
        return testFile;
    }

    public static List<Cost> getListOfRandomCosts(int size) {
        List<Cost> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(getRandomCost());
        }
        return list;
    }

    public static Cost getRandomCost() {
        Cost mock = mock(Cost.class);
        when(mock.getAmount()).thenReturn(getRandomBigDecimal());
        when(mock.getComment()).thenReturn(getRandomString());
        when(mock.getTimestamp()).thenReturn(getRandomDateOfLastYear());
        when(mock.getUuid()).thenReturn(UUID.randomUUID());
        return mock;
    }

    public static Cost cost(String comment, int amount) {
        Cost mock = costWithComment(comment);
        when(mock.getAmount()).thenReturn(new BigDecimal(amount));
        return mock;
    }

    public static Cost cost(String comment, int amount, int amountScale) {
        Cost mock = costWithComment(comment);
        when(mock.getAmount()).thenReturn(
                new BigDecimal(BigInteger.valueOf(amount), amountScale));
        return mock;
    }

    public static Cost cost(DateTime dateTime) {
        Cost cost = getRandomCost();
        when(cost.getTimestamp()).thenReturn(dateTime.toDate());
        return cost;
    }

    public static List<Cost> listOfCostsWithComments(String... comments) {
        List<Cost> list = new ArrayList<>();
        for (String each : comments) {
            list.add(costWithComment(each));
        }
        return list;
    }

    public static Cost costWithComment(String comment) {
        Cost mock = getRandomCost();
        when(mock.getComment()).thenReturn(comment);
        return mock;
    }
    
}
