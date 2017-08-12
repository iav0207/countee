package ru.takoe.iav.countee.view;

import java.lang.ref.WeakReference;

import javax.annotation.Nullable;
import javax.inject.Inject;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import ru.takoe.iav.countee.R;

import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

public class ViewProvider {

    private WeakReference<AppCompatActivity> activity;

    @Inject
    public ViewProvider(AppCompatActivity activity) {
        this.activity = new WeakReference<>(activity);
    }

    @Nullable
    public Toolbar getToolbar() {
        return findViewById(R.id.toolbar);
    }

    public Button getImportDataButton() {
        return findViewById(R.id.import_data_button);
    }

    public Button getExportDataButton() {
        return findViewById(R.id.export_data_button);
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
        if (isNull(activity.get())) {
            return null;
        }
        return (T) activity.get().findViewById(id);
    }

}
