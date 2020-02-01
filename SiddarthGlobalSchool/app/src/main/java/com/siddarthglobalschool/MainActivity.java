package com.siddarthglobalschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.siddarthglobalschool.Util.UtilMethods;
import com.siddarthglobalschool.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UtilMethods.INSTANCE.getLoginPrefd(MainActivity.this).equals("")) {
                    Intent i = new Intent(MainActivity.this,
                            LoginActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(MainActivity.this,
                            Dashboard.class);
                    startActivity(i);
                }
                finish();
                //the current activity will get finished.
            }
        }, 2000);
    }
}
