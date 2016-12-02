package ru.takoe.iav.countee.fragment.listener;

import android.content.res.AssetManager;
import android.view.View;
import android.widget.AdapterView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import ru.takoe.iav.countee.fragment.content.stats.StatsFragmentSelectionHolder;
import ru.takoe.iav.countee.fragment.content.stats.data.BarDataFacade;
import ru.takoe.iav.countee.view.spinner.MultiSpinner;

import javax.annotation.Nonnull;

/**
 * Created by takoe on 14.11.16.
 */
public class ChartItemSelectedListener implements AdapterView.OnItemSelectedListener,
        MultiSpinner.MultiSpinnerListener {

    private BarChart chart;

    private BarDataFacade barDataFacade;

    private StatsFragmentSelectionHolder selectionHolder;

    public ChartItemSelectedListener(@Nonnull BarChart chart, AssetManager assets) {
        this.chart = chart;
        barDataFacade = new BarDataFacade(assets);
        selectionHolder = new StatsFragmentSelectionHolder();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int selectedChartType, long l) {
        setData(getBarData(selectedChartType));
        adjustAxes();
        refresh();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // do nothing
    }

    @Override
    public void onItemsSelected(boolean[] selected) {
        setData(getBarData(selected));
        adjustAxes();
        refresh();
    }

    private void setData(BarData data) {
        chart.setData(data);
    }

    private void refresh() {
        chart.invalidate();
    }

    private BarData getBarData(int selectedItemNum) {
        selectionHolder.setChartType(selectedItemNum);
        return getData();
    }

    private BarData getBarData(boolean[] selected) {
        selectionHolder.setFilters(selected);
        return getData();
    }

    private BarData getData() {
        return barDataFacade.getData(selectionHolder.getChartType(), selectionHolder.getFilters());
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
