package ru.takoe.iav.countee.fragment.content.stats.data;

import android.content.res.AssetManager;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.FileUtils;

public class LineDataSetBuilder {

    private LineDataSet dataSet;

    public LineDataSetBuilder(AssetManager assets, String pathToDataFile) {
        dataSet = new LineDataSet(FileUtils.loadEntriesFromAssets(assets, pathToDataFile), "");
    }

    public LineDataSet build() {
        return dataSet;
    }

    public LineDataSetBuilder label(String label) {
        dataSet.setLabel(label);
        return this;
    }

    public LineDataSetBuilder lineWidth(float width) {
        dataSet.setLineWidth(width);
        return this;
    }

    public LineDataSetBuilder circleRadius(float radius) {
        dataSet.setCircleRadius(radius);
        return this;
    }

    public LineDataSetBuilder drawCircles(boolean enabled) {
        dataSet.setDrawCircles(enabled);
        return this;
    }

    public LineDataSetBuilder color(int color) {
        dataSet.setColor(color);
        return this;
    }

    public LineDataSetBuilder circleColor(int color) {
        dataSet.setCircleColor(color);
        return this;
    }

}
