package ru.takoe.iav.countee.fragment.content.stats.data;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import org.joda.time.DateTime;
import ru.iav.takoe.countee.service.ChartsDataService;

import java.util.List;
import java.util.Map;

/**
 * Created by takoe on 14.11.16.
 */
public abstract class AbstractBarDataProvider {

    private BarDataColorGenerator colorGenerator;

    private Typeface typeface;

    public AbstractBarDataProvider(AssetManager assets) {
        this.typeface = Typeface.createFromAsset(assets, "fonts/OpenSans-Regular.ttf");
        this.colorGenerator = new BarDataColorGenerator();
    }

    public BarData getBarData() {
        BarData data = new BarData(createDataSet());
        data.setValueTypeface(typeface);
        return data;
    }

    protected BarDataSet createDataSet() {
        BarDataSet dataSet = new BarDataSet(createEntries(), caption());
        colorGenerator.setDataColor(dataSet);
        return dataSet;
    }

    protected abstract List<BarEntry> createEntries();

    protected BarEntry entry(int i, Map.Entry<DateTime, Float> entry) {
        return new BarEntry(i, entry.getValue(), entry.getKey());
    }

    protected abstract Map<DateTime, Float> getDataFromService();

    protected ChartsDataService getDataService() {
        return ChartsDataService.getInstance();
    }

    protected abstract String caption();

    protected Typeface typeface() {
        return typeface;
    }

    protected BarDataColorGenerator colorGenerator() {
        return colorGenerator;
    }

}
