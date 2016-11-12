package ru.takoe.iav.countee.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.fragment.CreateCostFragment;
import ru.takoe.iav.countee.fragment.SettingsFragment;
import ru.takoe.iav.countee.fragment.StatsFragment;
import ru.takoe.iav.countee.view.ViewProvider;

import javax.annotation.Nonnull;

/**
 * Created by takoe on 21.08.16.
 */
class ViewRenderer {

    private final AppCompatActivity activity;

    private final ViewProvider viewProvider;

    ViewRenderer(@Nonnull AppCompatActivity activity) {
        this.activity = activity;
        this.viewProvider = new ViewProvider(activity);
    }

    void displayView(int viewId) {

        Fragment fragment = null;
        String title = activity.getString(R.string.app_name);

        switch (viewId) {
            case R.id.nav_add_cost:
                fragment = CreateCostFragment.newInstance(getViewProvider());
                title  = "Add cost";
                break;
            case R.id.nav_stats:
                fragment = StatsFragment.newInstance(getViewProvider());
                title = "Stats";
                break;
            case R.id.nav_settings:
                fragment = SettingsFragment.newInstance();
                title = "Settings";
                break;
        }

        if (fragment != null) {
            setActive(fragment);
            setToolbarTitle(title);
        }

        closeDrawer();
    }

    private void setActive(@Nonnull Fragment fragment) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    private void setToolbarTitle(String title) {
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setTitle(title);
        }
    }

    private void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private ViewProvider getViewProvider() {
        return viewProvider;
    }

}
