package ru.takoe.iav.countee.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import ru.takoe.iav.countee.R;

import javax.annotation.Nonnull;

/**
 * Created by takoe on 20.08.16.
 */
class DrawerListenerFactory {

    private final AppCompatActivity activity;

    DrawerListenerFactory(@Nonnull AppCompatActivity activity) {
        this.activity = activity;
    }

    DrawerLayout.DrawerListener createDrawerListener(DrawerLayout layout) {
        return new ActionBarDrawerToggle(activity, layout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                resetTitleAndOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                resetTitleAndOptionsMenu();
            }

            private void resetTitleAndOptionsMenu() {
                if (activity.getActionBar() != null) {
                    activity.getActionBar().setTitle(activity.getTitle());
                }
                activity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
    }

}
