package com.example.britto.mygiftapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by sambritto on 3/19/2018.
 */

public class SplashPage extends Activity {


    private  final int splash_dis_length =2000;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_main);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashPage.this,MainActivity.class);
                SplashPage.this.startActivity(mainIntent);
                SplashPage.this.finish();
            }
        }, splash_dis_length);
    }






}
