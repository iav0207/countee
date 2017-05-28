package ru.takoe.iav.countee.fragment.listener;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import android.content.res.AssetManager;
import android.view.View;
import android.widget.AdapterView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import ru.takoe.iav.countee.application.ApplicationLoader;
import ru.takoe.iav.countee.fragment.content.stats.StatsFragmentSelectionHolder;
import ru.takoe.iav.countee.fragment.content.stats.data.BarDataFacade;
import ru.takoe.iav.countee.view.spinner.MultiSpinner;

public class ChartItemSelectedListener implements AdapterView.OnItemSelectedListener,
        MultiSpinner.MultiSpinnerListener {

    private BarChart chart;

    @Inject BarDataFacade barDataFacade;

    @Inject StatsFragmentSelectionHolder selectionHolder;

    public ChartItemSelectedListener(@Nonnull BarChart chart, AssetManager assets) {
        this.chart = chart;
        ApplicationLoader.getInstance()
                .getStatsComponent(assets)
                .injectInto(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int selectedChartType, long l) {
        selectionHolder.setChartType(selectedChartType);
        rebuild();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        rebuild(selectionHolder.getFilters());
    }

    @Override
    public void onItemsSelected(boolean[] selected) {
        rebuild(selected);
    }

    private void rebuild(boolean[] selected) {
        selectionHolder.setFilters(selected);
        rebuild();
    }

    private void rebuild() {
        setData(getBarData());
        adjustAxes();
        chart.invalidate();
    }

    private BarData getBarData() {
        return barDataFacade.getData(selectionHolder.getChartType(), selectionHolder.getFilters());
    }

    private void setData(BarData data) {
        chart.setData(data);
    }

    private void adjustAxes() {
        BarData data = chart.getData();
        int selectedChartType = selectionHolder.getChartType();

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaximum(data.getYMax());
        leftAxis.setAxisMinimum(selectedChartType > 1 ? Math.min(data.getYMin(), 0f) : 0f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setAxisMinimum(data.getXMin() - 0.5f);
        xAxis.setAxisMaximum(data.getXMax() + 0.5f);
    }

}
