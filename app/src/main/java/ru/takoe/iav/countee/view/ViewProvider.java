package ru.takoe.iav.countee.view;

import javax.annotation.Nullable;
import javax.inject.Inject;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import ru.takoe.iav.countee.R;

public class ViewProvider {

    private AppCompatActivity activity;

    @Inject
    public ViewProvider(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Nullable
    public Toolbar getToolbar() {
        return findViewById(R.id.toolbar);
    }

    public NavigationView getNavigationView() {
        return findViewById(R.id.nav_view);
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
