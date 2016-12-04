package ru.takoe.iav.countee.fragment.content.stats;

import com.google.common.primitives.Booleans;

/**
 * Created by takoe on 01.12.16.
 */
public class StatsFragmentSelectionHolder {

    private int chartType = 0;

    private boolean[] filters = new boolean[StatsFragmentContent.getFilterSpinnerItems().size()];

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
        for (int i = 0; i < trues.length; i++) {
            trues[i] = true;
        }
        return trues;
    }

}
