package ru.takoe.iav.countee.fragment;

import javax.inject.Inject;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.apache.commons.lang3.StringUtils;
import ru.iav.takoe.countee.da.exception.CostNotSavedException;
import ru.iav.takoe.countee.service.CostOutputService;
import ru.iav.takoe.countee.service.SaveCostService;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.application.ApplicationLoader;
import ru.takoe.iav.countee.fragment.content.addcost.CreateCostPagerAdapter;
import ru.takoe.iav.countee.view.ViewProvider;
import ru.takoe.iav.countee.view.ViewScroller;

import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateCostFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateCostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateCostFragment extends Fragment {

    @Inject SaveCostService saveCostService;
    @Inject CostOutputService costOutputService;

    @Inject ViewProvider viewProvider;

    @BindView(R.id.create_cost_view_pager) ViewPager viewPager;
    @BindView(R.id.balance_text) TextView balanceOutput;
    @BindView(R.id.edit_message) EditText inputField;
    @BindView(R.id.save_cost_button) Button saveCostButton;

    @BindString(R.string.cost_not_saved_msg) String costNotSavedMsg;

    private FragmentStatePagerAdapter pagerAdapter;

    private OnFragmentInteractionListener mListener;

    private InputMethodManager keyboardManager;

    public CreateCostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateCostFragment.
     */
    public static CreateCostFragment newInstance() {
        CreateCostFragment fragment = new CreateCostFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApplicationLoader.getInstance()
                .getViewProviderComponent(getActivity())
                .injectInto(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.content_create_cost, container, false);

        ButterKnife.bind(this, rootView);

        pagerAdapter = new CreateCostPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshOutputOnStart();
    }

    private void refreshOutputOnStart() {
        getBalanceOutput().setText(getReadCostService().getCurrentBalanceOutput());
        setCurrentMonthView();
    }

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

    /**
     * Called when user clicks the Save button
     */
    @OnClick(R.id.save_cost_button)
    void saveCost() {
        try {
            getSaveCostService().saveAsNewCost(getInputText());
        } catch (CostNotSavedException ex) {
            hideKeyboard();
            showToast();
        } finally {
            clearInputText();
            refreshOutputOnCostSaving();
        }
    }

    private void hideKeyboard() {
        getKeyboardManager().hideSoftInputFromWindow(viewProvider.getNavigationView().getWindowToken(), 0);
    }

    private void showToast() {
        Toast.makeText(getContext(), costNotSavedMsg, Toast.LENGTH_SHORT).show();
    }

    private String getInputText() {
        return getInputField().getText().toString();
    }

    private void clearInputText() {
        getInputField().setText(StringUtils.EMPTY);
    }

    private void refreshOutputOnCostSaving() {
        getBalanceOutput().setText(getReadCostService().getCurrentBalanceOutput());
        setCurrentMonthView();
        getOutputArea().setText(getReadCostService().getCurrentMonthOutput());
        ViewScroller.scrollToBottom(getScrollView());
    }

    private InputMethodManager getKeyboardManager() {
        if (isNull(keyboardManager)) {
            keyboardManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        }
        return keyboardManager;
    }

    private void setCurrentMonthView() {
        viewPager.setCurrentItem(pagerAdapter.getCount());
    }

    private ScrollView getScrollView() {
        return viewProvider.getScrollView();
    }

    private TextView getOutputArea() {
        return viewProvider.getOutputArea();
    }

    private EditText getInputField() {
        return inputField;
    }

    private TextView getBalanceOutput() {
        return balanceOutput;
    }

    private CostOutputService getReadCostService() {
        return costOutputService;
    }

    private SaveCostService getSaveCostService() {
        return saveCostService;
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
