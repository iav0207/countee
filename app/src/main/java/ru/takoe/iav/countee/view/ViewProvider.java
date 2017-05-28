package ru.takoe.iav.countee.view;

import javax.annotation.Nullable;
import javax.inject.Inject;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.BindView;
import ru.takoe.iav.countee.R;

public class ViewProvider {

    private AppCompatActivity activity;

    @BindView(R.id.nav_view) NavigationView navigationView;

    @Nullable @BindView(R.id.toolbar) Toolbar toolbar;

    @Inject
    public ViewProvider(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Nullable
    public Toolbar getToolbar() {
        return toolbar;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public ScrollView getScrollView() {
        return findViewById(R.id.scrollableOutputText);
    }

    public TextView getOutputArea() {
        return findViewById(R.id.output_text);
    }

    @SuppressWarnings("unchecked")
    private <T extends View> T findViewById(int id) {
        return (T) activity.findViewById(id);
    }

}
