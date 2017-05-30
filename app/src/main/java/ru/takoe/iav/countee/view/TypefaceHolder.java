package ru.takoe.iav.countee.view;

import javax.inject.Inject;

import android.content.res.AssetManager;
import android.graphics.Typeface;

public class TypefaceHolder {

    private static String commonTypefacePath = "fonts/OpenSans-Light.ttf";

    private static String chartsTypefacePath = "fonts/OpenSans-Regular.ttf";

    private final AssetManager assets;

    private final Typeface commonTypeface;
    private final Typeface chartsTypeface;

    @Inject
    public TypefaceHolder(AssetManager assets) {
        this.assets = assets;

        commonTypeface = createTypeface(commonTypefacePath);
        chartsTypeface = createTypeface(chartsTypefacePath);
    }

    public Typeface getCommonTypeface() {
        return commonTypeface;
    }

    public Typeface getChartsTypeface() {
        return chartsTypeface;
    }

    private Typeface createTypeface(String path) {
        return Typeface.createFromAsset(assets, path);
    }

}
