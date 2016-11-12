package ru.takoe.iav.countee.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import ru.iav.takoe.countee.service.CostOutputService;
import ru.iav.takoe.countee.service.SaveCostService;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.view.ViewProvider;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateCostFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateCostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateCostFragment extends Fragment implements View.OnClickListener {

    private ViewProvider viewProvider;

    private OnFragmentInteractionListener mListener;

    public CreateCostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateCostFragment.
     */
    public static CreateCostFragment newInstance(ViewProvider viewProvider) {
        CreateCostFragment fragment = new CreateCostFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_create_cost, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewProvider.getSaveCostButton().setOnClickListener(this);
        updateOutputText();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    public void onClick(View view) {
        if (viewProvider.getSaveCostButton().getId() == view.getId()) {
            saveCost();
        }
    }

    /**
     * Called when user clicks the Save button
     */
    private void saveCost() {
        getSaveCostService().saveAsNewCost(getInputText());
        clearInputText();
        updateOutputText();
    }

    private String getInputText() {
        return getInputField().getText().toString();
    }

    private void clearInputText() {
        getInputField().setText("");
    }

    private void updateOutputText() {
        getOutputArea().setText(getReadCostService().getCurrentMonthOutput());
        getBalanceOutput().setText(getReadCostService().getCurrentBalanceOutput());
        ViewScroller.scrollToBottom(getScrollView());
    }

    private EditText getInputField() {
        return viewProvider.getInputField();
    }

    private ScrollView getScrollView() {
        return viewProvider.getScrollView();
    }

    private TextView getBalanceOutput() {
        return viewProvider.getBalanceOutput();
    }

    private TextView getOutputArea() {
        return viewProvider.getOutputArea();
    }

    private CostOutputService getReadCostService() {
        return CostOutputService.getInstance();
    }

    private SaveCostService getSaveCostService() {
        return SaveCostService.getInstance();
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
        void onFragmentInteraction(Uri uri);
    }

}
