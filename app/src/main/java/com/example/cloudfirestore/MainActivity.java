package com.example.cloudfirestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.example.cloudfirestore.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    @SuppressLint("StaticFieldLeak")
    private static Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        activity = this;
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        AddUserViewModel addUserViewModel = new AddUserViewModel();
        activityMainBinding.setAddUserViewModel(addUserViewModel);
    }

    public static Context getAppContext(){
        return context;
    }
    public static Activity getActivity(){
        return activity;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        context = null;
        activity = null;
    }
}