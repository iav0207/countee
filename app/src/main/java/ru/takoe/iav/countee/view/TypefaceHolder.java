package ru.takoe.iav.countee.view;

import android.content.res.AssetManager;
import android.graphics.Typeface;

/**
 * Created by takoe on 04.12.16.
 */
public class TypefaceHolder {

    private static String commonTypefacePath = "fonts/OpenSans-Light.ttf";

    public static Typeface getCommonTypeface(AssetManager assets) {
        return Typeface.createFromAsset(assets, commonTypefacePath);
    }

}
