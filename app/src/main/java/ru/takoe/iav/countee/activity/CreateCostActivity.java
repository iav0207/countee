package ru.takoe.iav.countee.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.fragment.CreateCostFragment;
import ru.takoe.iav.countee.fragment.SettingsFragment;
import ru.takoe.iav.countee.fragment.StatsFragment;
import ru.takoe.iav.countee.fragment.content.SettingsFragmentContent;
import ru.takoe.iav.countee.properties.ApplicationProperties;
import ru.takoe.iav.countee.view.ViewProvider;

public class CreateCostActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        CreateCostFragment.OnFragmentInteractionListener,
        StatsFragment.OnFragmentInteractionListener,
        SettingsFragment.OnListFragmentInteractionListener {

    private final ViewRenderer viewRenderer = new ViewRenderer(this);

    private final ViewProvider viewProvider = new ViewProvider(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cost);

        setSupportActionBar(viewProvider.getToolbar());
        addDrawerListener();
        viewProvider.getNavigationView().setNavigationItemSelectedListener(this);

        ApplicationProperties.setOutputDirectory(getFilesDir());

        viewRenderer.displayView(R.id.nav_add_cost);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.create_cost_button_message), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private void addDrawerListener() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, viewProvider.getToolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(SettingsFragmentContent.Item item) {

    }

}
