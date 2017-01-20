package ru.iav.takoe.countee.utils;

import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static ru.iav.takoe.countee.utils.StreamUtils.reverse;

/**
 * Created by takoe on 15.01.17.
 */
public class StreamUtilsTest {

    private List<Integer> inputList = collectToList(createInputStream());

    @Test
    public void shouldRevertStream() throws Exception {
        List<Integer> outputList = collectToList(reverseTimes(1));
        int n = inputList.size();
        for (int i = 0; i < n; i++) {
            assertEquals(outputList.get(i), inputList.get(n - i - 1));
        }
    }

    @Test
    public void shouldBeSymmetric() throws Exception {
        Stream<Integer> output = reverseTimes(2);
        assertNotEquals(output, createInputStream());
        assertEquals(collectToList(output), inputList);
    }

    private static Stream<Integer> reverseTimes(int times) {
        Stream<Integer> result = createInputStream();
        for (int i = 0; i < times; i++) {
            result = reverse(result);
        }
        return result;
    }

    private <T> List<T> collectToList(Stream<T> stream) {
        return stream.collect(toList());
    }

    private static Stream<Integer> createInputStream() {
        return Stream.of(1, 2, 3, 4, 5);
    }

}