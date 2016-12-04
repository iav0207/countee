package ru.takoe.iav.countee.fragment.content.stats.data;

import android.content.res.AssetManager;
import com.github.mikephil.charting.data.BarEntry;
import org.joda.time.DateTime;
import ru.iav.takoe.countee.service.CostCommentsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by takoe on 01.12.16.
 */
public class CostsMonthlyBarDataProvider extends CostsBarDataProvider {

    private static final String caption = "Costs, monthly";

    // TODO remove
    private CostCommentsService costCommentsService;

    public CostsMonthlyBarDataProvider(AssetManager assets) {
        super(assets);
        costCommentsService = CostCommentsService.getInstance();
    }

    @Override
    protected List<BarEntry> createEntries(String... filter) {
        List<BarEntry> entries = new ArrayList<>();
        int i = 0;
        for (Map.Entry<DateTime, Float> dailyRecord : getDataFromService(filter).entrySet()) {
            entries.add(entry(++i, dailyRecord));
        }
        return entries;
    }

    @Override
    protected Map<DateTime, Float> getDataFromService(String... filter) {
        return getDataService().getCostsMonthlyData(new TreeSet<>(Arrays.asList(filter)));
    }

    @Override
    protected List<BarEntry> createEntries() {
        List<BarEntry> entries = new ArrayList<>();
        int i = 0;
        for (Map.Entry<DateTime, Float> dailyRecord : getDataFromService().entrySet()) {
            entries.add(entry(++i, dailyRecord));
        }
        return entries;
    }

    @Override
    protected Map<DateTime, Float> getDataFromService() {
        return getDataService().getCostsMonthlyData(getAllComments());
    }

    private Set<String> getAllComments() {
        return costCommentsService.getAllCommentsSet();
    }

    @Override
    protected String caption() {
        return caption;
    }

}
