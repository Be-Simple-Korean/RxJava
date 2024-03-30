package com.example.rxjava.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rxjava.R;
import com.example.rxjava.network.model.ItemVO;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private Button btnSearch;
    private EditText etQuery;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initVar();
        initListener();
        initObserver();
    }

    /**
     * 변수 정의
     */
    private void initVar() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        btnSearch = findViewById(R.id.btn_search);
        etQuery = findViewById(R.id.et_query);
    }

    /**
     * 리스너 정의
     */
    private void initListener() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q = etQuery.getText().toString();
                viewModel.searchRepositories(q);
            }
        });
    }

    /**
     * 옵저버 정의
     */
    private void initObserver() {
        viewModel.itemList.observe(this, apiResult->{
           switch (apiResult.getStatus()){
               case SUCCESS:
                   Log.e("jh1030","SUCCESS");
                   Log.e("jh1030","SUCCESS DATA >> "+apiResult.getData());
                   break;
               case ERROR:
                   Log.e("jh1030","ERROR");
                   Log.e("jh1030","ERROR DATA >> "+apiResult.getError().getMessage());
                   break;
           }
        });
    }
}
