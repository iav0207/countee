package ru.takoe.iav.countee.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.takoe.iav.countee.fragment.content.stats.data.BarDataColorGenerator;

@Module(includes = {
        ServiceModule.class
})
public class AppModule {

    @Provides
    @Singleton
    BarDataColorGenerator provideBarDataColorGenerator() {
        return new BarDataColorGenerator();
    }

}
