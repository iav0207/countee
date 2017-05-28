package ru.takoe.iav.countee.fragment.content.addcost;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.iav.takoe.countee.service.CostOutputService;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.application.ApplicationLoader;
import ru.takoe.iav.countee.view.ViewScroller;

public class CreateCostPageFragment extends Fragment {

    @Inject CostOutputService costOutputService;

    @BindView(R.id.output_text) TextView outputArea;
    @BindView(R.id.scrollableOutputText) ScrollView scrollView;

    private int position;

    static CreateCostPageFragment newInstance(int position) {
        CreateCostPageFragment fragment = new CreateCostPageFragment();
        fragment.position = position;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_cost_output, container, false);

        ButterKnife.bind(this, rootView);
        ApplicationLoader.getInstance()
                .getApplicationComponent()
                .injectInto(this);

        updateOutputText();
        return rootView;
    }

    private void updateOutputText() {
        outputArea.setText(getReadCostService().getOutputForPrevMonth(position));
        ViewScroller.scrollToBottom(scrollView);
    }

    private CostOutputService getReadCostService() {
        return costOutputService;
    }

}
