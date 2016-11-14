package ru.takoe.iav.countee.fragment.content.stats;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import org.joda.time.DateTime;
import ru.iav.takoe.countee.service.FundsDataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by takoe on 12.11.16.
 */
public class BalanceBarDataProvider {

    private static final String caption = "Funds, daily";

    private BarDataColorGenerator colorGenerator;

    private Typeface typeface;

    public BalanceBarDataProvider(AssetManager assets) {
        this.typeface = Typeface.createFromAsset(assets, "fonts/OpenSans-Regular.ttf");
        this.colorGenerator = new BarDataColorGenerator();
    }

    public BarData getFundsBarData() {
        BarData data = new BarData(createDataSet());
        data.setValueTypeface(typeface);
        return data;
    }

    private BarDataSet createDataSet() {
        BarDataSet dataSet = new BarDataSet(createEntries(), caption);
        colorGenerator.setDataColor(dataSet);
        return dataSet;
    }

    private List<BarEntry> createEntries() {
        List<BarEntry> entries = new ArrayList<>();
        int i = 0;
        for (Map.Entry<DateTime, Float> dailyRecord : getDataFromService().entrySet()) {
            entries.add(entry(++i, dailyRecord));
        }
        return entries;
    }

    private BarEntry entry(int i, Map.Entry<DateTime, Float> entry) {
        return new BarEntry(i, entry.getValue(), entry.getKey());
    }

    private Map<DateTime, Float> getDataFromService() {
        return getFundsDataService().getFundsData();
    }

    private FundsDataService getFundsDataService() {
        return FundsDataService.getInstance();
    }

}
