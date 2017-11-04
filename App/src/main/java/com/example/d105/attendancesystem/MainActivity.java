package com.example.d105.attendancesystem;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        GIFView gifImageView = (GIFView) findViewById(R.id.GifImageView);
//        gifImageView.setGifImageResource(R.drawable.loader);

        session=new SessionManager(MainActivity.this);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                if(session.isLoggedIn()) {


                   Intent mainIntent = new Intent(MainActivity.this, NewNavigationMain.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();

                }
                else {
                    Intent mainIntent = new Intent(MainActivity.this, Login.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
