package com.example.rxjava;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BasicViewModel viewModel = new ViewModelProvider(this).get(BasicViewModel.class);

        viewModel.myData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("observe",s);
            }
        });
        Log.e("main","before load");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("main","call load");
                viewModel.loadData();
            }
        },2000);
    }
}