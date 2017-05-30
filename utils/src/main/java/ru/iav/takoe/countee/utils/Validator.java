package ru.iav.takoe.countee.utils;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface Validator<T> {

    /**
     * Provides validation of a given instance.
     *
     * @param object an instance to validate.
     * @throws IllegalArgumentException is validation fails.
     */
    void validate(T object);

}
