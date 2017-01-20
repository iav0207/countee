package ru.iav.takoe.countee.service;

import org.joda.time.DateTime;
import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.model.filter.impl.CostCommentFilter;
import ru.iav.takoe.countee.model.strategy.CostsDailyStrategy;
import ru.iav.takoe.countee.model.strategy.CostsMonthlyStrategy;
import ru.iav.takoe.countee.model.strategy.FundsDailyStrategy;
import ru.iav.takoe.countee.model.strategy.FundsMonthlyStrategy;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Nonnull
    public Map<DateTime, Float> getFundsDailyData() {
        return new FundsDailyStrategy(getAllCosts()).execute();
    }

    @Nonnull
    public Map<DateTime, Float> getFundsMonthlyData() {
        return new FundsMonthlyStrategy(getAllCosts()).execute();
    }

    @Nonnull
    public Map<DateTime, Float> getCostsDailyData(@Nonnull Set<String> commentsToFilter) {
        return new CostsDailyStrategy(getFilteredCosts(commentsToFilter)).execute();
    }

    @Nonnull
    public Map<DateTime, Float> getCostsMonthlyData(@Nonnull Set<String> commentsToFilter) {
        return new CostsMonthlyStrategy(getFilteredCosts(commentsToFilter)).execute();
    }

    @Nonnull
    private List<Cost> getFilteredCosts(@Nonnull Set<String> comments) {
        return CostCommentFilter.from(comments).filter(getAllCosts());
    }

    @Nonnull
    private List<Cost> getAllCosts() {
        return safeList(reader.readAllCosts());
    }

}
