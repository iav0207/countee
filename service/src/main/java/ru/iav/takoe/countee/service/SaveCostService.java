package ru.iav.takoe.countee.service;

import ru.iav.takoe.countee.da.CostSaver;
import ru.iav.takoe.countee.vo.Cost;

import static ru.iav.takoe.countee.logging.LogService.logError;

public class SaveCostService {

    private static SaveCostService instance = new SaveCostService();

    private CostSaver costSaver;

    private CostInputParser inputParser;

    public static SaveCostService getInstance() {
        return instance;
    }

    private SaveCostService() {
        costSaver = CostSaver.getInstance();
        inputParser = CostInputParser.getInstance();
    }

    public void saveAsNewCost(String input) {
        try {
            Cost newCost = inputParser.parseAsCost(input);
            costSaver.save(newCost);
        } catch (Exception e) {
            logError(e.getMessage(), e);
        }
    }



}
