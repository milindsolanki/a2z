package com.a2z.deliver.activities.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import com.a2z.deliver.activities.home.HomeActivity;
import com.a2z.deliver.activities.mainActivity.MainActivity;
import com.a2z.deliver.utils.SharedPref;

/**
 * Created by Aprod LLC. on 7/28/2018.
 */
public class SplashPresenter {

    android.os.Handler handler;
    Activity activity;

    public SplashPresenter(Activity activity){
        this.activity = activity;
    }

    public void setDetails(){

        handler = new android.os.Handler(  );
        handler.postDelayed( new Runnable() {
            @Override
            public void run() {
                Intent intent = null;
                if (SharedPref.getInstance( activity ).getLoginDetails() != null){
                    intent = new Intent( activity, HomeActivity.class );
                } else {
                    intent = new Intent( activity, MainActivity.class );
                }
                activity.startActivity( intent );
                activity.finish();
            }
        }, 3000 );
    }
}
