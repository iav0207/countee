package ru.takoe.iav.countee.fragment.content.stats;

/**
 * Created by takoe on 01.12.16.
 */
public class StatsFragmentSelectionHolder {

    private int chartType = 0;

    private boolean[] filters = new boolean[1];

    public int getChartType() {
        return chartType;
    }

    public void setChartType(int chartType) {
        this.chartType = chartType;
    }

    public boolean[] getFilters() {
        return filters;
    }

    public void setFilters(boolean[] filters) {
        this.filters = filters;
    }
}
