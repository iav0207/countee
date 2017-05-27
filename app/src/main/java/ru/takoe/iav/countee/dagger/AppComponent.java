package ru.takoe.iav.countee.dagger;

import javax.inject.Singleton;

import dagger.Component;
import ru.iav.takoe.countee.service.CostOutputService;
import ru.iav.takoe.countee.service.SaveCostService;
import ru.takoe.iav.countee.fragment.CreateCostFragment;

/**
 * Created by takoe on 20.01.17.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void injectInto(CreateCostFragment fragment);

    SaveCostService getSaveCostService();

    CostOutputService getCostOutputService();

}
