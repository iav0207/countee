package ru.takoe.iav.countee.fragment;

import javax.inject.Inject;

import android.support.v4.app.Fragment;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.ScatterData;
import ru.takoe.iav.countee.fragment.content.stats.data.BarDataGenerator;

/**
 * Created by takoe on 10.11.16.
 */
public class AbstractChartFragment extends Fragment {

    @Inject BarDataGenerator dataGenerator;

    public AbstractChartFragment() {
        // Required empty public constructor
    }

    protected BarData generateBarData(int dataSets, float range, int count) {
        return dataGenerator.generateBarData(dataSets, range, count);
    }

    protected ScatterData generateScatterData(int dataSets, float range, int count) {
        return dataGenerator.generateScatterData(dataSets, range, count);
    }

    protected PieData generatePieData() {
        return dataGenerator.generatePieData();
    }

    protected LineData generateLineData() {
        return dataGenerator.generateLineData();
    }

    protected LineData getComplexity() {
        return dataGenerator.getComplexity();
    }

}
