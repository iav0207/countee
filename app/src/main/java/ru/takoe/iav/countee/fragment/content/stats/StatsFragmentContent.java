package ru.takoe.iav.countee.fragment.content.stats;

import android.content.res.Resources;
import ru.iav.takoe.countee.service.CostCommentsService;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.fragment.content.common.StringItemList;

public class StatsFragmentContent {

    private static CostCommentsService costCommentsService = CostCommentsService.getInstance();

    public static StringItemList getChartSpinnerItems(Resources resources) {
        String[] itemNames = resources.getStringArray(R.array.charts_array);
        return StringItemList.fromStrings(itemNames);
    }

    public static StringItemList getFilterSpinnerItems() {
        return StringItemList.fromStrings(costCommentsService.getAllCommentsSet());
    }

}
