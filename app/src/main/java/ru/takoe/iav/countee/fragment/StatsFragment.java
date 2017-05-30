package ru.takoe.iav.countee.fragment;

import javax.inject.Inject;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import ru.iav.takoe.countee.service.CostCommentsService;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.application.CounteeApp;
import ru.takoe.iav.countee.fragment.content.common.StringItem;
import ru.takoe.iav.countee.fragment.content.common.StringItemList;
import ru.takoe.iav.countee.fragment.content.stats.SimpleMarkerView;
import ru.takoe.iav.countee.fragment.content.stats.adapter.ChartsRecyclerViewAdapter;
import ru.takoe.iav.countee.fragment.content.stats.adapter.FilterRecyclerViewAdapter;
import ru.takoe.iav.countee.fragment.listener.ChartItemSelectedListener;
import ru.takoe.iav.countee.view.TypefaceHolder;
import ru.takoe.iav.countee.view.spinner.MultiSpinner;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsFragment extends AbstractChartFragment implements OnChartGestureListener {

    @Inject CostCommentsService costCommentsService;
    @Inject TypefaceHolder typefaceHolder;

    @BindView(R.id.bar_chart_1) BarChart mChart;
    @BindView(R.id.chartSpinner) Spinner chartTypeSpinner;
    @BindView(R.id.filterSpinner) MultiSpinner filterSpinner;

    @BindArray(R.array.charts_array) String[] itemNames;

    private OnFragmentInteractionListener mListener;

    private Typeface typeface;

    public StatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StatsFragment.
     */
    public static StatsFragment newInstance() {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CounteeApp.getInstance()
                .getStatsComponent(getActivity())
                .injectInto(this);

        typeface = typefaceHolder.getCommonTypeface();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        ButterKnife.bind(this, view);
        setUpChart();
        createSpinners();

        // setChartData(); chart data is set on dropdown list activation

        adjustLegend();
        adjustAxes();
        refresh();

        return view;
    }

    private void createSpinners() {
        ChartItemSelectedListener listener = new ChartItemSelectedListener(mChart, getActivity().getAssets());
        addItemsOnChartSpinner(listener);
        addItemsOnFilterSpinner(listener);
    }

    private void addItemsOnChartSpinner(AdapterView.OnItemSelectedListener listener) {
        StringItemList items = StringItemList.fromStrings(itemNames);

        chartTypeSpinner.setAdapter(new ChartsRecyclerViewAdapter(items, mListener));
        chartTypeSpinner.setOnItemSelectedListener(listener);
    }

    private void addItemsOnFilterSpinner(MultiSpinner.MultiSpinnerListener listener) {
        StringItemList items = StringItemList.fromStrings(costCommentsService.getAllCommentsSet());

        filterSpinner.setAdapter(new FilterRecyclerViewAdapter(items, mListener));
        filterSpinner.setItems(items, listener);
    }

    private void setUpChart() {
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
