package ru.iav.takoe.countee.da;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface Saver<Entity, Ex extends Exception> {

    void save(Entity entity) throws Ex;

}
