package ru.iav.takoe.countee.service;

import ru.iav.takoe.countee.da.CostSaver;
import ru.iav.takoe.countee.da.Invalidable;
import ru.iav.takoe.countee.da.exception.CostNotSavedException;
import ru.iav.takoe.countee.vo.Cost;

import static ru.iav.takoe.countee.logging.LogService.logError;

// TODO cover with tests
public class SaveCostService {

    private static SaveCostService instance = new SaveCostService();

    private CostSaver costSaver;

    private CostInputParser inputParser;

    private Invalidable monthOutputService;

    public static SaveCostService getInstance() {
        return instance;
    }

    private SaveCostService() {
        costSaver = CostSaver.getInstance();
        inputParser = CostInputParser.getInstance();
        monthOutputService = MonthOutputService.getInstance();
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
