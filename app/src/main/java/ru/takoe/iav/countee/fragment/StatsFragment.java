package ru.takoe.iav.countee.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Spinner;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.fragment.content.common.StringItem;
import ru.takoe.iav.countee.fragment.content.common.StringItemList;
import ru.takoe.iav.countee.fragment.content.stats.ChartsRecyclerViewAdapter;
import ru.takoe.iav.countee.fragment.content.stats.SimpleMarkerView;
import ru.takoe.iav.countee.fragment.content.stats.StatsFragmentContent;
import ru.takoe.iav.countee.fragment.content.stats.data.FundsDailyBarDataProvider;
import ru.takoe.iav.countee.fragment.listener.ChartItemSelectedListener;
import ru.takoe.iav.countee.properties.ApplicationProperties;
import ru.takoe.iav.countee.view.ViewProvider;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsFragment extends AbstractChartFragment implements OnChartGestureListener {

    private ViewProvider viewProvider;

    private OnFragmentInteractionListener mListener;

    private FundsDailyBarDataProvider dataProvider;

    private BarChart mChart;

    private Typeface typeface;

    public StatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param viewProvider an instance of currently used view provider to inject.
     * @return A new instance of fragment StatsFragment.
     */
    public static StatsFragment newInstance(ViewProvider viewProvider) {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.viewProvider = viewProvider;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        setUpChart(view);
        createTypeface();
        addItemsOnSpinner(view);

        // setChartData(); chart data is set on dropdown list activation

        adjustLegend();
        adjustAxes();
        refresh();

        return view;
    }

    private void addItemsOnSpinner(View view) {
        Spinner chartTypeSpinner = (Spinner) view.findViewById(R.id.chartSpinner);
        StringItemList items = StatsFragmentContent.getItems(getResources());

        chartTypeSpinner.setAdapter(new ChartsRecyclerViewAdapter(items, mListener));
        chartTypeSpinner.setOnItemSelectedListener(new ChartItemSelectedListener(mChart, getActivity().getAssets()));
    }

    private void setUpChart(View view) {
        mChart = (BarChart) view.findViewById(R.id.bar_chart_1);
        mChart.getDescription().setEnabled(false);
        mChart.setOnChartGestureListener(this);

        mChart.setMarker(createMarkerView());

        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
    }

    private MarkerView createMarkerView() {
        SimpleMarkerView mv = new SimpleMarkerView(getActivity(), R.layout.simple_marker_view);
        mv.setChartView(mChart); // For bounds control
        return mv;
    }

    private void createTypeface() {
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");
    }

    private void setChartData() {
        if (ApplicationProperties.isGenerateRandomDataForCharts()) {
            initializeDataGenerator();
            mChart.setData(generateBarData(1, 20000, 12));
        } else {
            dataProvider = new FundsDailyBarDataProvider(getActivity().getAssets());
            mChart.setData(dataProvider.getBarData());
        }
    }

    private void adjustLegend() {
        mChart.getLegend().setTypeface(typeface);
    }

    private void adjustAxes() {
        mChart.getAxisLeft().setTypeface(typeface);
        mChart.getXAxis().setTypeface(typeface);
        mChart.getAxisRight().setEnabled(false);
        mChart.getXAxis().setEnabled(false);
    }

    private void refresh() {
        mChart.invalidate();
    }

    private void addChartToLayout(View view) {
        FrameLayout statsLayout = (FrameLayout) view.findViewById(R.id.statsLayout);
        statsLayout.addView(mChart);
    }

    public void onButtonPressed(StringItem item) {
        if (mListener != null) {
            mListener.onListFragmentInteraction(item);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START");
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END");
        mChart.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onListFragmentInteraction(StringItem item);
    }

}
