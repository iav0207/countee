package ru.takoe.iav.countee.fragment.content.stats.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.res.AssetManager;
import com.github.mikephil.charting.data.BarEntry;
import org.joda.time.DateTime;
import ru.takoe.iav.countee.application.CounteeApp;

public class FundsDailyBarDataProvider extends AbstractBarDataProvider {

    private static final String caption = "Funds, daily";

    public FundsDailyBarDataProvider(AssetManager assets) {
        CounteeApp.getInstance()
                .getStatsComponent(assets)
                .injectInto(this);
    }

    @Override
    protected List<BarEntry> createEntries() {
        List<BarEntry> entries = new ArrayList<>();
        int i = 0;
        for (Map.Entry<DateTime, Float> dailyRecord : getDataFromService().entrySet()) {
            entries.add(entry(++i, dailyRecord));
        }
        return entries;
    }

    @Override
    protected Map<DateTime, Float> getDataFromService() {
        return getDataService().getFundsDailyData();
    }

    @Override
    protected String caption() {
        return caption;
    }

}
