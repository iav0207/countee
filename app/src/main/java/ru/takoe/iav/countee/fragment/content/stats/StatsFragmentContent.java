package ru.takoe.iav.countee.fragment.content.stats;

import android.content.res.Resources;
import ru.iav.takoe.countee.service.CostCommentsService;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.fragment.content.common.StringItemList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by takoe on 15.11.16.
 */
public class StatsFragmentContent {

    private static CostCommentsService costCommentsService = CostCommentsService.getInstance();

    public static StringItemList getChartSpinnerItems(Resources resources) {
        String[] itemNames = resources.getStringArray(R.array.charts_array);
        return StringItemList.fromStrings(itemNames);
    }

    public static List<String> getFilterSpinnerItemsList() {
        List<String> list = new ArrayList<>();
        for (String each : costCommentsService.getAllCommentsSet()) {
            list.add(each);
        }
        return list;
    }

    public static StringItemList getFilterSpinnerItems() {
        Set<String> set = costCommentsService.getAllCommentsSet();
        Iterator<String> iterator = costCommentsService.getAllCommentsSet().iterator();
        String[] commentsArray = new String[set.size()];
        for (int i = 0; iterator.hasNext(); i++) {
            commentsArray[i] = iterator.next();
        }
        return StringItemList.fromStrings(commentsArray);
    }

}
