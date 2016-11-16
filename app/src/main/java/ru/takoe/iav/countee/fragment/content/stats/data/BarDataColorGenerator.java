package ru.takoe.iav.countee.fragment.content.stats.data;

import android.graphics.Color;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import static ru.iav.takoe.countee.utils.TestUtils.getRandomInteger;

/**
 * Created by takoe on 14.11.16.
 */
class BarDataColorGenerator {

    private static final int PRIMARY_COLOR = Color.parseColor("#9982BA");

    private static final int[] COLOR_BASE = ColorTemplate.MATERIAL_COLORS;

    void setDataColor(final BarDataSet dataSet) {
        dataSet.setColor(getRandomBaseColor());
    }

    void setDataColors(final BarDataSet dataSet) {
        int[] colors = new int[dataSet.getEntryCount()];
        for (int i = 0; i < colors.length; i++) {
            int colorByValue = getColorByValue(min(dataSet), max(dataSet), getValue(dataSet, i));
            colors[i] = merge(PRIMARY_COLOR, merge(getRandomBaseColor(), colorByValue));
        }
        dataSet.setColors(colors);
    }

    private static float min(IBarDataSet dataSet) {
        return dataSet.getYMin();
    }

    private static float max(IBarDataSet dataSet) {
        return dataSet.getYMax();
    }

    private static float getValue(IBarDataSet dataSet, int index) {
        return dataSet.getEntryForIndex(index).getY();
    }

    private static int getColorByValue(float min, float max, float value) {
        return Color.rgb(
                Math.round( (max - value) / (max - min) ) * 195 + 30,
                30,
                Math.round( (value - min) / (max - min) ) * 100 + 100
        );
    }

    private static int merge(int color1, int color2) {
        return Color.rgb(
                avg(Color.red(color1), Color.red(color2)),
                avg(Color.green(color1), Color.green(color2)),
                avg(Color.blue(color1), Color.blue(color2))
        );
    }

    private static int avg(int one, int two) {
        return Math.round((one + two) / 2);
    }

    private static int getRandomBaseColor() {
        return COLOR_BASE[getRandomInteger(COLOR_BASE.length)];
    }

}
