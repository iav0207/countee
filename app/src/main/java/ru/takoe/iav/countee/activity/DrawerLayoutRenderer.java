package ru.takoe.iav.countee.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ru.takoe.iav.countee.R;

import javax.annotation.Nonnull;

/**
 * Created by takoe on 20.08.16.
 */
class DrawerLayoutRenderer {

    private final DrawerListenerFactory drawerListenerFactory;

    private final AppCompatActivity activity;

    DrawerLayoutRenderer(@Nonnull AppCompatActivity activity) {
        this.activity = activity;
         drawerListenerFactory = new DrawerListenerFactory(activity);
    }

    void renderDrawer() {
        String[] navigationMenuItems = activity.getResources().getStringArray(R.array.navigation_elements);
        getDrawerList().setAdapter(new ArrayAdapter<>(activity, R.layout.nav_list_item, navigationMenuItems));
        getDrawerList().setOnItemClickListener(new DrawerItemClickListener());
        getDrawerLayout().addDrawerListener(drawerListenerFactory.createDrawerListener(getDrawerLayout()));
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        getDrawerList().setItemChecked(position, true);
        activity.setTitle("xex)");
        getDrawerLayout().closeDrawer(getDrawerList());
    }

    private DrawerLayout getDrawerLayout() {
        return (DrawerLayout) activity.findViewById(R.id.drawer_layout);
    }

    private ListView getDrawerList() {
        return (ListView) activity.findViewById(R.id.left_drawer);
    }

}
