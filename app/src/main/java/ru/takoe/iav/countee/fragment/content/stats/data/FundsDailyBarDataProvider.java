package ru.takoe.iav.countee.fragment.content.stats.data;

import android.content.res.AssetManager;
import com.github.mikephil.charting.data.BarEntry;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by takoe on 12.11.16.
 */
public class FundsDailyBarDataProvider extends AbstractBarDataProvider {

    private static final String caption = "Funds, daily";

    public FundsDailyBarDataProvider(AssetManager assets) {
        super(assets);
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
        return getFundsDataService().getFundsDailyData();
    }

    @Override
    protected String caption() {
        return caption;
    }

}
