package com.example.ordersystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref=getSharedPreferences("data", Context.MODE_PRIVATE);
        int num=pref.getInt("allDishNumber",-50);
        if(num==-50) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("allDishNumber", 0);
            editor.commit();
        }

        if(Build.VERSION.SDK_INT>=18) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }

        initProgressBar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,MainMenu.class);
                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        },1500);
    }

    private void initProgressBar(){
        dialog=new AlertDialog.Builder(this)
                .setView(R.layout.enter_progress_dialog_view)
                .setCancelable(false)
                .show();
    }
}
