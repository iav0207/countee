package ru.takoe.iav.countee.fragment.content.stats.data;

import android.content.res.AssetManager;
import com.github.mikephil.charting.data.BarEntry;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by takoe on 14.11.16.
 */
public class FundsMonthlyDataProvider extends AbstractBarDataProvider {

    private static final String caption = "Funds, monthly";

    public FundsMonthlyDataProvider(AssetManager assets) {
        super(assets);
    }

    @Override
    protected List<BarEntry> createEntries() {
        List<BarEntry> entries = new ArrayList<>();
        int i = 0;
        for (Map.Entry<DateTime, Float> monthlyRecord : getDataFromService().entrySet()) {
            entries.add(entry(++i, monthlyRecord));
        }
        return entries;
    }

    @Override
    protected Map<DateTime, Float> getDataFromService() {
        return getFundsDataService().getFundsMonthlyData();
    }

    @Override
    protected String caption() {
        return caption;
    }
}
