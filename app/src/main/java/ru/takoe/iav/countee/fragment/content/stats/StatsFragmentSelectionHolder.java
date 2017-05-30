package ru.takoe.iav.countee.fragment.content.stats;

import java.util.Arrays;

import com.google.common.primitives.Booleans;
import ru.iav.takoe.countee.service.CostCommentsService;
import ru.takoe.iav.countee.application.CounteeApp;

public class StatsFragmentSelectionHolder {

    private int chartType = 0;

    private boolean[] filters;

    public StatsFragmentSelectionHolder() {
        CostCommentsService costCommentsService = CounteeApp.getInstance()
                .getApplicationComponent()
                .getCostCommentsService();
        int filterItemsCount = costCommentsService.getAllCommentsSet().size();

        this.filters = new boolean[filterItemsCount];
    }

    public int getChartType() {
        return chartType;
    }

    public void setChartType(int chartType) {
        this.chartType = chartType;
    }

    public boolean[] getFilters() {
        if (isNoneSelected()) {
            return allTrue();
        }
        return filters;
    }

    public void setFilters(boolean[] filters) {
        this.filters = filters;
    }

    private boolean isNoneSelected() {
        return Booleans.countTrue(filters) == 0;
    }

    private boolean[] allTrue() {
        boolean[] trues = new boolean[filters.length];
        Arrays.fill(trues, true);
        return trues;
    }

}
