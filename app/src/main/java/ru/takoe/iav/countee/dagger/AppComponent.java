package ru.takoe.iav.countee.dagger;

import javax.inject.Singleton;

import dagger.Component;
import ru.iav.takoe.countee.service.CostCommentsService;
import ru.iav.takoe.countee.service.CostOutputService;
import ru.iav.takoe.countee.service.SaveCostService;
import ru.takoe.iav.countee.fragment.content.addcost.CreateCostPageFragment;
import ru.takoe.iav.countee.fragment.content.addcost.CreateCostPagerAdapter;

@Singleton
@Component(
        modules = AppModule.class
)
public interface AppComponent {

    SaveCostService getSaveCostService();

    CostOutputService getCostOutputService();

    CostCommentsService getCostCommentsService();

    void injectInto(CreateCostPageFragment createCostPageFragment);

    void injectInto(CreateCostPagerAdapter createCostPagerAdapter);

}
