package ru.takoe.iav.countee.fragment.content.stats;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by takoe on 10.11.16.
 */
public class BarDataGenerator {

    private Typeface typeface;

    private AssetManager assets;

    public BarDataGenerator(AssetManager assets) {
        this.typeface = Typeface.createFromAsset(assets, "fonts/OpenSans-Regular.ttf");
        this.assets = assets;
    }

    public BarData generateBarData(int dataSets, float range, int count) {

        ArrayList<IBarDataSet> sets = new ArrayList<>();

        for(int i = 0; i < dataSets; i++) {

            ArrayList<BarEntry> entries = new ArrayList<>();

//            entries = FileUtils.loadEntriesFromAssets(assets, "stacked_bars.txt");

            for(int j = 0; j < count; j++) {
                entries.add(new BarEntry(j, (float) (Math.random() * range) + range / 4));
            }

            BarDataSet ds = new BarDataSet(entries, getLabel(i));
            ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
            sets.add(ds);
        }

        BarData d = new BarData(sets);
        d.setValueTypeface(typeface);
        return d;
    }

    public ScatterData generateScatterData(int dataSets, float range, int count) {

        ArrayList<IScatterDataSet> sets = new ArrayList<>();

        ScatterChart.ScatterShape[] shapes = ScatterChart.ScatterShape.getAllDefaultShapes();

        for(int i = 0; i < dataSets; i++) {

            ArrayList<Entry> entries = new ArrayList<>();

            for(int j = 0; j < count; j++) {
                entries.add(new Entry(j, (float) (Math.random() * range) + range / 4));
            }

            ScatterDataSet ds = new ScatterDataSet(entries, getLabel(i));
            ds.setScatterShapeSize(12f);
            ds.setScatterShape(shapes[i % shapes.length]);
            ds.setColors(ColorTemplate.COLORFUL_COLORS);
            ds.setScatterShapeSize(9f);
            sets.add(ds);
        }

        ScatterData d = new ScatterData(sets);
        d.setValueTypeface(typeface);
        return d;
    }

    /**
     * generates less data (1 DataSet, 4 values)
     * @return
     */
    public PieData generatePieData() {

        int count = 4;

        ArrayList<PieEntry> entries1 = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "Quarter " + (i+1)));
        }

        PieDataSet ds1 = new PieDataSet(entries1, "Quarterly Revenues 2015");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);
        d.setValueTypeface(typeface);

        return d;
    }

    public LineData generateLineData() {

        final float lineWidth = 2f;

        ArrayList<ILineDataSet> sets = new ArrayList<>();

        sets.add(new LineDataSetBuilder(assets, "data/sine.txt")
                .label("Sine function")
                .lineWidth(lineWidth)
                .drawCircles(false)
                .color(ColorTemplate.VORDIPLOM_COLORS[0])
                .build());
        sets.add(new LineDataSetBuilder(assets, "data/cosine.txt")
                .label("Cosine function")
                .lineWidth(lineWidth)
                .drawCircles(false)
                .color(ColorTemplate.VORDIPLOM_COLORS[1])
                .build());

        LineData d = new LineData(sets);
        d.setValueTypeface(typeface);
        return d;
    }

    public LineData getComplexity() {

        ArrayList<ILineDataSet> sets = new ArrayList<>();

        final float lineWidth = 2.5f;
        final float circleRadius = 3f;

        sets.add(new LineDataSetBuilder(assets, "data/n.txt")
                .label("O(n)")
                .color(ColorTemplate.VORDIPLOM_COLORS[0])
                .circleColor(ColorTemplate.VORDIPLOM_COLORS[0])
                .lineWidth(lineWidth)
                .circleRadius(circleRadius)
                .build());
        sets.add(new LineDataSetBuilder(assets, "data/nlogn.txt")
                .label("O(nlogn)")
                .color(ColorTemplate.VORDIPLOM_COLORS[1])
                .circleColor(ColorTemplate.VORDIPLOM_COLORS[1])
                .lineWidth(lineWidth)
                .circleRadius(circleRadius)
                .build());
        sets.add(new LineDataSetBuilder(assets, "data/square.txt")
                .label("O(n\u00B2)")
                .color(ColorTemplate.VORDIPLOM_COLORS[2])
                .circleColor(ColorTemplate.VORDIPLOM_COLORS[2])
                .lineWidth(lineWidth)
                .circleRadius(circleRadius)
                .build());
        sets.add(new LineDataSetBuilder(assets, "data/three.txt")
                .label("O(n\u00B3)")
                .color(ColorTemplate.VORDIPLOM_COLORS[3])
                .circleColor(ColorTemplate.VORDIPLOM_COLORS[3])
                .lineWidth(lineWidth)
                .circleRadius(circleRadius)
                .build());

        LineData d = new LineData(sets);
        d.setValueTypeface(typeface);
        return d;
    }



    private LineDataSet newLineDataSet(String pathToDataFile, String label) {
        return new LineDataSetBuilder(assets, pathToDataFile)
                .label(label)
                .build();
    }

    private String[] mLabels = new String[] { "Company A", "Company B", "Company C", "Company D", "Company E", "Company F" };
//    private String[] mXVals = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec" };

    private String getLabel(int i) {
        return mLabels[i];
    }

}
