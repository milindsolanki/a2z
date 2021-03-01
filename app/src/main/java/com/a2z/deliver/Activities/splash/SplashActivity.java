package com.a2z.deliver.activities.splash;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.a2z.deliver.BaseApp;
import com.a2z.deliver.activities.home.HomeActivity;
import com.a2z.deliver.activities.mainActivity.MainActivity;
import com.a2z.deliver.R;
import com.a2z.deliver.utils.SharedPref;

public class SplashActivity extends BaseApp {

    SplashPresenter splashPresenter;
    SharedPref sharedPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );

        sharedPref = SharedPref.getInstance( this );
        splashPresenter = new SplashPresenter(this);
        splashPresenter.setDetails();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashPresenter = null;
    }
}
