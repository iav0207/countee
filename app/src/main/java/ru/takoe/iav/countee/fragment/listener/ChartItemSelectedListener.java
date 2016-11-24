package ru.takoe.iav.countee.fragment.listener;

import android.content.res.AssetManager;
import android.view.View;
import android.widget.AdapterView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import ru.takoe.iav.countee.fragment.content.stats.data.FundsDailyBarDataProvider;
import ru.takoe.iav.countee.fragment.content.stats.data.FundsMonthlyDataProvider;

import javax.annotation.Nonnull;

/**
 * Created by takoe on 14.11.16.
 */
public class ChartItemSelectedListener implements AdapterView.OnItemSelectedListener {

    private BarChart chart;

    private FundsDailyBarDataProvider fundsDailyBarDataProvider;

    private FundsMonthlyDataProvider fundsMonthlyDataProvider;

    public ChartItemSelectedListener(@Nonnull BarChart chart, AssetManager assets) {
        this.chart = chart;
        this.fundsDailyBarDataProvider = new FundsDailyBarDataProvider(assets);
        this.fundsMonthlyDataProvider = new FundsMonthlyDataProvider(assets);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if ((i % 2) > 0) {
            chart.setData(getMonthlyData());
        } else {
            chart.setData(getDailyData());
        }
        adjustAxes();
        chart.invalidate();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // do nothing
    }

    private BarData getDailyData() {
        return fundsDailyBarDataProvider.getFundsBarData();
    }

    private BarData getMonthlyData() {
        return fundsMonthlyDataProvider.getFundsBarData();
    }

    private void adjustAxes() {
        BarData data = chart.getData();

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaximum(data.getYMax());
        leftAxis.setAxisMinimum(0f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setAxisMinimum(data.getXMin() - 0.5f);
        xAxis.setAxisMaximum(data.getXMax() + 0.5f);
    }

}
