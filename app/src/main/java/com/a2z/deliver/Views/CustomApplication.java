package com.a2z.deliver.views;

import android.app.Application;
import android.content.Context;

import com.a2z.deliver.R;

/**
 * Created by Aprod LLC. on 7/26/2018.
 */
public class CustomApplication extends Application {
    private static Context context;
    CustomFontFamily customFontFamily;
    @Override
    public void onCreate() {
        super.onCreate();
        CustomApplication.context = this;
        customFontFamily = CustomFontFamily.getInstance();
        // add your custom fonts here with your own custom name.
        customFontFamily.addFont("app_font", getResources().getString(R.string.font));
        customFontFamily.addFont("app_fontBold", getResources().getString(R.string.fontBold));
    }
    public static Context getContext() {
        return context;
    }

}
