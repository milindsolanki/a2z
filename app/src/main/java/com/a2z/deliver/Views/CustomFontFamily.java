package com.a2z.deliver.views;

import android.graphics.Typeface;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Aprod LLC. on 7/26/2018.
 */
public class CustomFontFamily {
    static CustomFontFamily customFontFamily;
    HashMap<String, String> fontMap = new HashMap<>();
    public static CustomFontFamily getInstance() {
        if (customFontFamily == null)
            customFontFamily = new CustomFontFamily();
        return customFontFamily;
    }
    public void addFont(String alias, String fontName) {
        fontMap.put(alias, fontName);
    }
    public Typeface getFont(String alias) {
        String fontFilename = fontMap.get(alias);
        if (fontFilename == null) {
            Log.e("", "Font not available with name " + alias);
            return null;
        } else {
            Typeface typeface = Typeface.createFromAsset(CustomApplication.getContext().getAssets(), fontFilename);
            return typeface;
        }
    }

}
