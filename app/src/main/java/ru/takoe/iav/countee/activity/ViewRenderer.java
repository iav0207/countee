package ru.takoe.iav.countee.activity;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindString;
import butterknife.ButterKnife;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.fragment.CreateCostFragment;
import ru.takoe.iav.countee.fragment.SettingsFragment;
import ru.takoe.iav.countee.fragment.StatsFragment;
import ru.takoe.iav.countee.view.ViewProvider;


public class ViewRenderer {

    private AppCompatActivity activity;

    private ViewProvider viewProvider;

    @BindString(R.string.app_name) String appName;
    @BindString(R.string.nav_add_cost) String navAddCost;
    @BindString(R.string.nav_stats) String navStats;
    @BindString(R.string.nav_settings) String navSettings;

    @Inject
    public ViewRenderer(@Nonnull AppCompatActivity activity, ViewProvider viewProvider) {
        this.activity = activity;
        this.viewProvider = viewProvider;
    }

    void displayView(int viewId) {

        if (appName == null) ButterKnife.bind(this, activity);

        final Fragment fragment;
        final String title;

        switch (viewId) {
            case R.id.nav_add_cost:
                fragment = CreateCostFragment.newInstance();
                title  = navAddCost;
                break;
            case R.id.nav_stats:
                fragment = StatsFragment.newInstance();
                title = navStats;
                break;
            case R.id.nav_settings:
                fragment = SettingsFragment.newInstance(getViewProvider());
                title = navSettings;
                break;
            default:
                fragment = null;
                title = appName;
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
