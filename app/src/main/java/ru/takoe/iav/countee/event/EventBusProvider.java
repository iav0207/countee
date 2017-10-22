package ru.takoe.iav.countee.event;

import javax.annotation.ParametersAreNonnullByDefault;

import org.greenrobot.eventbus.EventBus;

@ParametersAreNonnullByDefault
public class EventBusProvider {

    public static EventBus eventBus() {
        return EventBus.getDefault();
    }

    private EventBusProvider() {}

}
