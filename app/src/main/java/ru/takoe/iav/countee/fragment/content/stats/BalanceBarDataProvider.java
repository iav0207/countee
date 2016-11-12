package ru.takoe.iav.countee.fragment.content.stats;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import org.joda.time.DateTime;
import ru.iav.takoe.countee.service.FundsDataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.iav.takoe.countee.utils.TestUtils.getRandomInteger;

/**
 * Created by takoe on 12.11.16.
 */
public class BalanceBarDataProvider {

    private static final int[] color_base = ColorTemplate.PASTEL_COLORS;

    private static final String caption = "Funds, daily";

    private Typeface typeface;

    public BalanceBarDataProvider(AssetManager assets) {
        this.typeface = Typeface.createFromAsset(assets, "fonts/OpenSans-Regular.ttf");
    }

    public BarData getFundsBarData() {
        BarData data = new BarData(createDataSet());
        data.setValueTypeface(typeface);
        return data;
    }

    private BarDataSet createDataSet() {
        BarDataSet dataSet = new BarDataSet(createEntries(), caption);
        setDataColors(dataSet);
        return dataSet;
    }

    private void setDataColors(BarDataSet dataSet) {
        float minValue = dataSet.getYMin();
        float maxValue = dataSet.getYMax();
        int[] colors = new int[dataSet.getEntryCount()];
        for (int i = 0; i < colors.length; i++) {
            float y = dataSet.getEntryForIndex(i).getY();
            int red = Math.round( (maxValue - y) / (maxValue - minValue) ) * 195 + 30;
            int green = 30;
            int blue = Math.round( (y - minValue) / (maxValue - minValue) ) * 195 + 30;
            colors[i] = mergeWithColorBase(Color.rgb(red, green, blue));
        }
        dataSet.setColors(colors);
    }

    private int mergeWithColorBase(int color) {
        int baseColor = color_base[getRandomInteger(color_base.length)];
        return Color.rgb(
                avg(Color.red(color), Color.red(baseColor)),
                avg(Color.green(color), Color.green(baseColor)),
                avg(Color.blue(color), Color.blue(baseColor))
        );
    }

    private int avg(int one, int two) {
        return Math.round((one + two) / 2);
    }

    private int[] getColorBase(int count) {
        int[] colors = new int[count];
        for (int i = 0; i < count; i++) {
            colors[i] = color_base[i % color_base.length];
        }
        return colors;
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
