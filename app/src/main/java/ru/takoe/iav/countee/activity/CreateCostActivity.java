package ru.takoe.iav.countee.activity;

import javax.inject.Inject;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.application.CounteeApp;
import ru.takoe.iav.countee.fragment.CreateCostFragment;
import ru.takoe.iav.countee.fragment.SettingsFragment;
import ru.takoe.iav.countee.fragment.StatsFragment;
import ru.takoe.iav.countee.fragment.content.common.StringItem;
import ru.takoe.iav.countee.fragment.content.settings.SettingsFragmentContent;
import ru.takoe.iav.countee.view.ViewProvider;

import static ru.takoe.iav.countee.properties.ApplicationProperties.applicationProperties;

public class CreateCostActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        CreateCostFragment.OnFragmentInteractionListener,
        StatsFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener {

    @Inject ViewProvider viewProvider;
    @Inject ViewRenderer viewRenderer;

    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) @Nullable Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cost);

        CounteeApp.getInstance()
                .getViewProviderComponent(this)
                .inject(this);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        addDrawerListener();
        navigationView.setNavigationItemSelectedListener(this);

        applicationProperties().setOutputDirectory(getFilesDir());

        viewRenderer.displayView(R.id.nav_add_cost);
    }

    private void addDrawerListener() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void setTitle(CharSequence title) {
        if (getActionBar() != null)
            getActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        viewRenderer.displayView(item.getItemId());
        return true;
    }

    @Override
    public void onListFragmentInteraction(StringItem item) {

    }

    @Override
    public void onListFragmentInteraction(SettingsFragmentContent.Item item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
