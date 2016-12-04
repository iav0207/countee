package ru.takoe.iav.countee.fragment.content.stats.data;

import android.content.res.AssetManager;
import com.github.mikephil.charting.data.BarData;
import ru.iav.takoe.countee.service.CostCommentsService;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by takoe on 01.12.16.
 */
public class BarDataFacade {

    private CostCommentsService costCommentsService;

    private FundsDailyBarDataProvider fundsDailyBarDataProvider;

    private FundsMonthlyDataProvider fundsMonthlyDataProvider;

    private CostsDailyBarDataProvider costsDailyBarDataProvider;

    private CostsMonthlyBarDataProvider costsMonthlyBarDataProvider;

    public BarDataFacade(AssetManager assets) {
        costCommentsService = CostCommentsService.getInstance();
        this.fundsDailyBarDataProvider = new FundsDailyBarDataProvider(assets);
        this.fundsMonthlyDataProvider = new FundsMonthlyDataProvider(assets);
        this.costsDailyBarDataProvider = new CostsDailyBarDataProvider(assets);
        this.costsMonthlyBarDataProvider = new CostsMonthlyBarDataProvider(assets);
    }

    public BarData getData(int chartType, boolean[] filterSelections) {
        switch (chartType) {
            case 0: return fundsDailyBarDataProvider.getBarData();
            case 1: return fundsMonthlyDataProvider.getBarData();
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