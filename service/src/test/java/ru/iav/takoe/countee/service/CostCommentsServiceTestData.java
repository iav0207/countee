package ru.iav.takoe.countee.service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.testng.annotations.DataProvider;

import static ru.iav.takoe.countee.service.utils.CounteeTestUtils.cost;
import static ru.iav.takoe.countee.service.utils.CounteeTestUtils.listOfCostsWithComments;

public class CostCommentsServiceTestData {

    @DataProvider(name = "lowerCaseComments")
    public static Object[][] lowerCaseComments() {
        return new Object[][] {{
            listOfCostsWithComments("a", "a", "a", "b", "12", "-&щ", ""),
                set("a", "b", "12", "-&щ", "")
        }};
    }

    @DataProvider(name = "differentCaseComments")
    public static Object[][] differentCaseComments() {
        return new Object[][] {{
                listOfCostsWithComments("a", "A", "a", "b", "12", "-&Щ", "fHhgO i?", ""),
                set("a", "b", "12", "-&щ", "", "fhhgo i?")
        }};
    }

    @DataProvider(name = "disorderedComments")
    public static Object[][] disorderedComments() {
        return new Object[][] {{
                listOfCostsWithComments("c", "b", "a", "-&Щ", "", "a", "12", "c", "Ъ"),
                list("", "-&щ", "12", "a", "b", "c", "ъ")
        }};
    }

    @DataProvider(name = "filterZeroTotals")
    public static Object[][] filterZeroTotals() {
        return new Object[][] {{
                Arrays.asList(
                        cost("a", 5),
                        cost("c", 5),
                        cost("a", -5),
                        cost("b", 8, 2),
                        cost("b", -8, 5)),
                set("c")
        }};
    }

    private static Set<String> set(String... comments) {
        return new TreeSet<>(list(comments));
    }

    private static List<String> list(String... comments) {
        return Arrays.asList(comments);
    }

}
