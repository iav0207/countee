package ru.takoe.iav.countee.dagger;

import javax.inject.Singleton;

import dagger.Component;
import ru.iav.takoe.countee.service.CostCommentsService;
import ru.iav.takoe.countee.service.CostOutputService;
import ru.iav.takoe.countee.service.SaveCostService;
import ru.takoe.iav.countee.fragment.content.stats.data.BarDataFacade;

/**
 * Created by takoe on 20.01.17.
 */
@Singleton
@Component(
        modules = AppModule.class
)
public interface AppComponent {

    SaveCostService getSaveCostService();

    CostOutputService getCostOutputService();

    CostCommentsService getCostCommentsService();

    void injectInto(BarDataFacade barDataFacade);

}
