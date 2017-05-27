package ru.iav.takoe.countee.service.utils;

import org.joda.time.DateTime;
import ru.iav.takoe.countee.vo.Cost;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomBigDecimal;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomDateOfLastYear;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

public class CounteeTestUtils {

    public static List<Cost> getListOfRandomCosts(int size) {
        List<Cost> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(getRandomCost());
        }
        return list;
    }

    public static Cost getRandomCost() {
        Cost mock = mock(Cost.class);
        doReturn(getRandomBigDecimal()).when(mock).getAmount();
        doReturn(getRandomString()).when(mock).getComment();
        doReturn(getRandomDateOfLastYear()).when(mock).getTimestamp();
        doReturn(UUID.randomUUID()).when(mock).getUuid();
        return mock;
    }

    public static Cost cost(DateTime dateTime) {
        Cost cost = mock(Cost.class);
        doReturn(dateTime.toDate()).when(cost).getTimestamp();
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
        Cost mock = mock(Cost.class);
        doReturn(comment).when(mock).getComment();
        return mock;
    }
    
}
