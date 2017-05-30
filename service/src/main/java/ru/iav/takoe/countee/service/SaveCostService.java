package ru.iav.takoe.countee.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.iav.takoe.countee.da.CostSaver;
import ru.iav.takoe.countee.da.exception.CostNotSavedException;
import ru.iav.takoe.countee.vo.Cost;

import static ru.iav.takoe.countee.logging.LogService.logError;

// TODO cover with tests
@Singleton
public class SaveCostService {

    @Inject CostSaver costSaver;

    @Inject CostInputParser inputParser;

    @Inject MonthOutputService monthOutputService;

    public SaveCostService() {}

    @Inject
    public SaveCostService(CostSaver costSaver, CostInputParser inputParser,
            MonthOutputService monthOutputService)
    {
        this.costSaver = costSaver;
        this.inputParser = inputParser;
        this.monthOutputService = monthOutputService;
    }

    public void saveAsNewCost(String input) throws CostNotSavedException {
        try {
            Cost newCost = inputParser.parseAsCost(input);
            costSaver.save(newCost);
        } catch (CostNotSavedException e) {
            logError("Cost not saved exception caught @ service layer!", e);
            throw e;
        } catch (RuntimeException e) {
            logError(e.getMessage(), e);
            throw new CostNotSavedException("Thrown from the service layer!", e);
        } finally {
            monthOutputService.invalidate();
        }
    }

}
