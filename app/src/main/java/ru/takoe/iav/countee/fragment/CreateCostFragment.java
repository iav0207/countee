package ru.takoe.iav.countee.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.apache.commons.lang3.StringUtils;
import ru.iav.takoe.countee.da.exception.CostNotSavedException;
import ru.iav.takoe.countee.service.CostCommentsService;
import ru.iav.takoe.countee.service.CostOutputService;
import ru.iav.takoe.countee.service.SaveCostService;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.android.util.SpaceTokenizer;
import ru.takoe.iav.countee.application.CounteeApp;
import ru.takoe.iav.countee.fragment.content.addcost.CreateCostPagerAdapter;
import ru.takoe.iav.countee.view.ViewProvider;
import ru.takoe.iav.countee.view.ViewScroller;

import javax.inject.Inject;
import java.util.ArrayList;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.chop;
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
    @Inject CostCommentsService costCommentsService;

    @Inject ViewProvider viewProvider;

    @BindView(R.id.create_cost_view_pager) ViewPager viewPager;
    @BindView(R.id.balance_text) TextView balanceOutput;
    @BindView(R.id.edit_message) MultiAutoCompleteTextView inputField;
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

        CounteeApp.getInstance()
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

        setupAutocompleteTextField();

        return rootView;
    }

    private void setupAutocompleteTextField() {
        getInputField().setTokenizer(new SpaceTokenizer());
        getInputField().setInputType(InputType.TYPE_CLASS_TEXT);
        getInputField().setThreshold(1);
        getInputField().setAdapter(createAutocompleteAdapter());
    }

    private ArrayAdapter<String> createAutocompleteAdapter() {
        return new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_item,
                new ArrayList<>(costCommentsService.getAllCommentsSet()));
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
            getSaveCostService().saveAsNewCost(chopTrailingWhitespace(getInputText()));
        } catch (CostNotSavedException ex) {
            hideKeyboard();
            showToast();
        } finally {
            clearInputText();
            refreshOutputOnCostSaving();
        }
    }

    private static String chopTrailingWhitespace(String text) {
        return text.endsWith(SPACE) ? chop(text) : text;
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

    private MultiAutoCompleteTextView getInputField() {
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
