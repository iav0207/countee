package ru.takoe.iav.countee.fragment.listener;

import android.content.res.AssetManager;
import android.view.View;
import android.widget.AdapterView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import ru.takoe.iav.countee.fragment.content.stats.data.CostsDailyBarDataProvider;
import ru.takoe.iav.countee.fragment.content.stats.data.CostsMonthlyBarDataProvider;
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

    private CostsDailyBarDataProvider costsDailyBarDataProvider;

    private CostsMonthlyBarDataProvider costsMonthlyBarDataProvider;

    public ChartItemSelectedListener(@Nonnull BarChart chart, AssetManager assets) {
        this.chart = chart;
        this.fundsDailyBarDataProvider = new FundsDailyBarDataProvider(assets);
        this.fundsMonthlyDataProvider = new FundsMonthlyDataProvider(assets);
        this.costsDailyBarDataProvider = new CostsDailyBarDataProvider(assets);
        this.costsMonthlyBarDataProvider = new CostsMonthlyBarDataProvider(assets);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        chart.setData(getBarData(i));
        adjustAxes(i);
        chart.invalidate();
    }

    private BarData getBarData(int selectedItemNum) {
        switch (selectedItemNum) {
            case 0: return fundsDailyBarDataProvider.getBarData();
            case 1: return fundsMonthlyDataProvider.getBarData();
            case 2: return costsDailyBarDataProvider.getBarData();
            case 3: return costsMonthlyBarDataProvider.getBarData();
            default: return fundsDailyBarDataProvider.getBarData();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // do nothing
    }

    private void adjustAxes(int selectedItemNum) {
        BarData data = chart.getData();

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaximum(data.getYMax());
        leftAxis.setAxisMinimum(selectedItemNum > 1 ? data.getYMin() : 0f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setAxisMinimum(data.getXMin() - 0.5f);
        xAxis.setAxisMaximum(data.getXMax() + 0.5f);
    }

}
