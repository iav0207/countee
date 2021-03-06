package ru.iav.takoe.countee.crypt.impl;

import javax.annotation.ParametersAreNonnullByDefault;

import static ru.iav.takoe.countee.crypt.impl.Constants.DEFAULT_KEY;

@ParametersAreNonnullByDefault
abstract class SimpleGostAlgorithmExecutor {

    private static KeyMerger keyMerger = new KeyMerger();

    private static KeyFitter keyFitter = new KeyFitter();

    protected final String key;

    protected SimpleGostAlgorithmExecutor(String key) {
        this.key = mergeWithDefaultKey(fitKeyLength(key));
    }

    protected SimpleGostAlgorithmExecutor() {
        key = DEFAULT_KEY;
    }

    private static String fitKeyLength(String key) {
        return keyFitter.fitLength(key);
    }

    private static String mergeWithDefaultKey(String key) {
        return keyMerger.mergeWithDefaultKey(key);
    }

}
