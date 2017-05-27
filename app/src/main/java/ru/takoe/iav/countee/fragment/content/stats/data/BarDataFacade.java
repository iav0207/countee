package ru.takoe.iav.countee.fragment.content.stats.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import android.content.res.AssetManager;
import com.github.mikephil.charting.data.BarData;
import ru.iav.takoe.countee.service.CostCommentsService;
import ru.takoe.iav.countee.application.ApplicationLoader;

public class BarDataFacade {

    @Inject CostCommentsService costCommentsService;

    @Inject FundsDailyBarDataProvider fundsDailyBarDataProvider;
    @Inject FundsMonthlyBarDataProvider fundsMonthlyBarDataProvider;
    @Inject CostsDailyBarDataProvider costsDailyBarDataProvider;
    @Inject CostsMonthlyBarDataProvider costsMonthlyBarDataProvider;

    public BarDataFacade(AssetManager assets) {
        ApplicationLoader.getInstance()
                .getStatsComponent(assets)
                .injectInto(this);
    }

    public BarData getData(int chartType, boolean[] filterSelections) {
        switch (chartType) {
            case 0: return fundsDailyBarDataProvider.getBarData();
            case 1: return fundsMonthlyBarDataProvider.getBarData();
            case 2: return costsDailyBarDataProvider.getBarData(toFilterItems(filterSelections));
            case 3: return costsMonthlyBarDataProvider.getBarData(toFilterItems(filterSelections));
            default: return fundsDailyBarDataProvider.getBarData();
        }
    }

    private String[] toFilterItems(boolean[] selections) {
        List<String> filteredComments = new ArrayList<>();
        Iterator<String> allComments = costCommentsService.getAllCommentsSet().iterator();
        for (int i = 0; allComments.hasNext() && i < selections.length; i++) {
            String next = allComments.next();
            if (selections[i]) {
                filteredComments.add(next);
            }
        }
        return toArray(filteredComments);
    }

    private String[] toArray(@Nonnull List<String> list) {
        String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

}
