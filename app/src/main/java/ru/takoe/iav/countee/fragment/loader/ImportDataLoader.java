package ru.takoe.iav.countee.fragment.loader;

import javax.annotation.ParametersAreNonnullByDefault;

import android.support.v4.content.AsyncTaskLoader;
import ru.takoe.iav.countee.application.CounteeApp;

@ParametersAreNonnullByDefault
public class ImportDataLoader extends AsyncTaskLoader<String> {

    private final String password;

    public ImportDataLoader(String password) {
        super(CounteeApp.getInstance().getApplicationContext());
        this.password = password;
    }

    @Override
    public String loadInBackground() {
        return null;
    }
}
