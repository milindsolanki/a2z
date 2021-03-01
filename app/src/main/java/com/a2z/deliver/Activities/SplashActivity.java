package com.a2z.deliver.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.a2z.deliver.R;
import com.a2z.deliver.Utils.SharedPref;

import java.util.logging.Handler;

import static com.a2z.deliver.Utils.SharedPref.LOGIN_DETAILS;

public class SplashActivity extends AppCompatActivity {

    android.os.Handler handler;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );

        sharedPref = new SharedPref( this );
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility( uiOptions );

//            Hides ActionBar
//            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//            actionBar.hide();
        }

        handler = new android.os.Handler(  );
        handler.postDelayed( new Runnable() {
            @Override
            public void run() {
                Intent intent = null;
                if (sharedPref.getLoginDetails() != null){
                    intent = new Intent( SplashActivity.this, HomeActivity.class );
                } else {
                    intent = new Intent( SplashActivity.this, MainActivity.class );
                }
                startActivity( intent );
                finish();
            }
        }, 3000 );
       /* handler.postDelayed( new Runnable( ) {
            @Override
            public void run() {
                Intent intent = null;
                if (sharedPref.getRegisterDetails( ) != null) {
                    intent = new Intent( SplashActivity.this, HomeActivity.class );
                } else {
                    intent = new Intent( SplashActivity.this, MainActivity.class );
                }
                startActivity( intent );
                finish( );
            }

        },3000 );*/
    }
}
