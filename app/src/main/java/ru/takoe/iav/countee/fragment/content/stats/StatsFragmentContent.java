package ru.takoe.iav.countee.fragment.content.stats;

import android.content.res.Resources;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.fragment.content.common.StringItemList;

/**
 * Created by takoe on 15.11.16.
 */
public class StatsFragmentContent {

    public static StringItemList getItems(Resources resources) {
        String[] itemNames = resources.getStringArray(R.array.charts_array);
        return StringItemList.fromStrings(itemNames);
    }

}
