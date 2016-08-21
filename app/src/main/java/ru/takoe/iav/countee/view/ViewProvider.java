package ru.takoe.iav.countee.view;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import ru.takoe.iav.countee.R;

/**
 * Created by takoe on 21.08.16.
 */
public class ViewProvider {

    private final AppCompatActivity activity;

    public ViewProvider(AppCompatActivity activity) {
        this.activity = activity;
    }

    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
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

    protected View findViewById(int id) {
        return activity.findViewById(id);
    }

}
