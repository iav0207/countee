package ru.takoe.iav.countee.fragment.content.stats.data;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import android.graphics.Typeface;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import org.joda.time.DateTime;
import ru.iav.takoe.countee.service.ChartsDataService;
import ru.takoe.iav.countee.view.TypefaceHolder;

public abstract class AbstractBarDataProvider {

    @Inject BarDataColorGenerator colorGenerator;

    @Inject ChartsDataService chartsDataService;

    @Inject TypefaceHolder typefaceHolder;

    BarData getBarData() {
        BarData data = new BarData(createDataSet());
        data.setValueTypeface(typeface());
        return data;
    }

    private BarDataSet createDataSet() {
        BarDataSet dataSet = new BarDataSet(createEntries(), caption());
        colorGenerator.setDataColor(dataSet);
        return dataSet;
    }

    protected abstract List<BarEntry> createEntries();

    BarEntry entry(int i, Map.Entry<DateTime, Float> entry) {
        return new BarEntry(i, entry.getValue(), entry.getKey());
    }

    protected abstract Map<DateTime, Float> getDataFromService();

    ChartsDataService getDataService() {
        return chartsDataService;
    }

    protected abstract String caption();

    protected Typeface typeface() {
        return typefaceHolder.getChartsTypeface();
    }

    BarDataColorGenerator colorGenerator() {
        return colorGenerator;
    }

}
