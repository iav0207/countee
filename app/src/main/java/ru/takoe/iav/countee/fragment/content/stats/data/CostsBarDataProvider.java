package ru.takoe.iav.countee.fragment.content.stats.data;

import android.content.res.AssetManager;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

/**
 * Created by takoe on 01.12.16.
 */
public abstract class CostsBarDataProvider extends AbstractBarDataProvider {

    public CostsBarDataProvider(AssetManager assets) {
        super(assets);
    }

    public BarData getBarData(String... filter) {
        BarData data = new BarData(createDataSet(filter));
        data.setValueTypeface(typeface());
        return data;
    }

    protected BarDataSet createDataSet(String... filter) {
        BarDataSet dataSet = new BarDataSet(createEntries(filter), caption());
        colorGenerator().setDataColor(dataSet);
        return dataSet;
    }

    protected abstract List<BarEntry> createEntries(String... filter);

    protected abstract Map<DateTime, Float> getDataFromService(String... filter);

}
