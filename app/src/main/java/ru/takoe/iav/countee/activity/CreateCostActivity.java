package ru.takoe.iav.countee.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import ru.iav.takoe.countee.service.CostOutputService;
import ru.iav.takoe.countee.service.SaveCostService;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.properties.ApplicationProperties;

public class CreateCostActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cost);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.create_cost_button_message), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        ApplicationProperties.setOutputDirectory(getFilesDir());
        updateOutputText();
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
        int id = item.getItemId();

        if (id == R.id.nav_stats) {

        } else if (id == R.id.nav_settings) {

        } else {
            // do nothing
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Called when user clicks the Save button
     */
    public void saveCost(View view) {
        getSaveCostService().saveAsNewCost(getInputText());
        clearInputText();
        updateOutputText();
    }

    private String getInputText() {
        return getInputField().getText().toString();
    }

    private void clearInputText() {
        getInputField().setText("");
    }

    private void updateOutputText() {
        getOutputArea().setText(getReadCostService().getCurrentMonthOutput());
        getBalanceOutput().setText(getReadCostService().getCurrentBalance());
        ViewScroller.scrollToBottom(getScrollView());
    }

    private EditText getInputField() {
        return (EditText) findViewById(R.id.edit_message);
    }

    private ScrollView getScrollView() {
        return (ScrollView) findViewById(R.id.scrollableOutputText);
    }

    private TextView getBalanceOutput() {
        return (TextView) findViewById(R.id.balance_text);
    }

    private TextView getOutputArea() {
        return (TextView) findViewById(R.id.output_text);
    }

    private CostOutputService getReadCostService() {
        return CostOutputService.getInstance();
    }

    private SaveCostService getSaveCostService() {
        return SaveCostService.getInstance();
    }

}
