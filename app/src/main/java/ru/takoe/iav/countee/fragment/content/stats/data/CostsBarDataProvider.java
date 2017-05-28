package ru.takoe.iav.countee.fragment.content.stats.data;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import android.content.res.AssetManager;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import org.joda.time.DateTime;
import ru.iav.takoe.countee.service.CostCommentsService;
import ru.takoe.iav.countee.application.ApplicationLoader;

public abstract class CostsBarDataProvider extends AbstractBarDataProvider {

    @Inject CostCommentsService costCommentsService;

    public CostsBarDataProvider(AssetManager assets) {
        super(assets);
        ApplicationLoader.getInstance()
                .getStatsComponent(assets)
                .injectInto(this);
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

    protected Set<String> getAllComments() {
        return costCommentsService.getAllCommentsSet();
    }

    protected abstract List<BarEntry> createEntries(String... filter);

    protected abstract Map<DateTime, Float> getDataFromService(String... filter);

}
