package ru.iav.takoe.countee.service;

import org.joda.time.DateTime;
import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.model.strategy.FundsDailyStrategy;
import ru.iav.takoe.countee.model.strategy.FundsMonthlyStrategy;
import ru.iav.takoe.countee.vo.Cost;

import java.util.List;
import java.util.Map;

import static ru.iav.takoe.countee.utils.ObjectUtils.safeList;

/**
 * Created by takoe on 12.11.16.
 */
public class ChartsDataService {

    private static ChartsDataService instance = new ChartsDataService();

    private CostReader reader;

    private ChartsDataService() {
        reader = CostReader.getInstance();
    }

    public static ChartsDataService getInstance() {
        return instance;
    }

    public Map<DateTime, Float> getFundsDailyData() {
        return new FundsDailyStrategy(getAllCosts()).execute();
    }

    public Map<DateTime, Float> getFundsMonthlyData() {
        return new FundsMonthlyStrategy(getAllCosts()).execute();
    }

    private List<Cost> getAllCosts() {
        return safeList(reader.readAllCosts());
    }

}
