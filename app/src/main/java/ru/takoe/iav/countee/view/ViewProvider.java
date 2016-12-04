package ru.takoe.iav.countee.view;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import ru.takoe.iav.countee.R;

/**
 * Created by takoe on 21.08.16.
 */
public class ViewProvider {

    private final FragmentActivity activity;

    public ViewProvider(FragmentActivity activity) {
        this.activity = activity;
    }

    public static ViewProvider createFor(Fragment fragment) {
        return new ViewProvider(fragment.getActivity());
    }

    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    public Button getSaveCostButton() {
        return (Button) findViewById(R.id.save_cost_button);
    }

    public NavigationView getNavigationView() {
        return (NavigationView) findViewById(R.id.nav_view);
    }

    public EditText getInputField() {
        return (EditText) findViewById(R.id.edit_message);
    }

    public ScrollView getScrollView() {
        return (ScrollView) findViewById(R.id.scrollableOutputText);
    }

    public TextView getBalanceOutput() {
        return (TextView) findViewById(R.id.balance_text);
    }

    public TextView getOutputArea() {
        return (TextView) findViewById(R.id.output_text);
    }

    public ViewPager getViewPager() {
        return (ViewPager) findViewById(R.id.create_cost_view_pager);
    }

    public FrameLayout getStatsLayout() {
        return (FrameLayout) findViewById(R.id.statsLayout);
    }

    protected View findViewById(int id) {
        return activity.findViewById(id);
    }

}
