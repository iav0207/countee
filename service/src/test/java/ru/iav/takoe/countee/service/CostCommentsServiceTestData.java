package ru.iav.takoe.countee.service;

import org.testng.annotations.DataProvider;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

    private static Set<String> set(String... comments) {
        return new TreeSet<>(list(comments));
    }

    private static List<String> list(String... comments) {
        return Arrays.asList(comments);
    }

}
