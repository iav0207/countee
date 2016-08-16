package ru.takoe.iav.countee.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import ru.iav.takoe.countee.service.ReadCostService;
import ru.iav.takoe.countee.service.SaveCostService;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.properties.ApplicationProperties;

public class CreateCostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cost);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_cost, menu);
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

    /**
     * Called when user clicks the Save button
     */
    public void saveCost(View view) {
//        Intent intent = new Intent(this, SaveCostActivity.class);
        getSaveCostService().saveAsNewCost(getInputText());
        clearInputText();
        updateOutputText();
        ViewScroller.scrollToBottom(getScrollView());
        /*intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);*/
    }

    private String getInputText() {
        return getInputField().getText().toString();
    }

    private void clearInputText() {
        getInputField().setText("");
    }

    private void updateOutputText() {
        getOutputArea().setText(getReadCostService().getCurrentMonthOutput());
    }

    private EditText getInputField() {
        return (EditText) findViewById(R.id.edit_message);
    }

    private ScrollView getScrollView() {
        return (ScrollView) findViewById(R.id.scrollableOutputText);
    }

    private TextView getOutputArea() {
        return (TextView) findViewById(R.id.output_text);
    }

    private ReadCostService getReadCostService() {
        return ReadCostService.getInstance();
    }

    private SaveCostService getSaveCostService() {
        return SaveCostService.getInstance();
    }

}
