package ru.iav.takoe.countee.model.map;

import org.testng.annotations.DataProvider;
import ru.iav.takoe.countee.vo.Cost;

import java.util.List;

import static ru.iav.takoe.countee.model.utils.CounteeTestUtils.listOfCostsWithComments;


/**
 * Created by takoe on 30.11.16.
 */
public class CostCommentFilterTestData {

    static final CostCommentFilter
            simpleFilter = CostCommentFilter.from("a", "b", "c", " ", "010 t"),
            nullableFilter = CostCommentFilter.from("a", "b", null, "c", " ", "", "010 t");

    @DataProvider(name = "simpleSet")
    public static Object[][] simpleSet() {
        return new Object[][] {{
            listOfCostsWithComments("??%", "b", "11111", "010 t", "a"),
                listOfCostsWithComments("b", "010 t", "a")
        }};
    }

    @DataProvider(name = "nullableSet")
    public static Object[][] nullableSet() {
        return new Object[][] {{
            plusNull(listOfCostsWithComments(null, "b", "11111", "01", "a")),
                listOfCostsWithComments("b", "a")
        }};
    }

    @DataProvider(name = "upperCaseSet")
    public static Object[][] upperCaseSet() {
        return new Object[][] {{
            listOfCostsWithComments("??%", "B", "11111", "010 T", "A"),
                listOfCostsWithComments("B", "010 T", "A")
        }};
    }

    private static List<Cost> plusNull(List<Cost> costs) {
        costs.add(null);
        return costs;
    }

}
