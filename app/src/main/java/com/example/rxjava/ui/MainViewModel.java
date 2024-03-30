package com.example.rxjava.ui;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rxjava.network.ApiResult;
import com.example.rxjava.network.model.ItemVO;
import com.example.rxjava.network.repository.GithubRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private ArrayList<ItemVO> list = new ArrayList<>();
    private final MutableLiveData<ApiResult<ArrayList<ItemVO>>> _itemList = new MutableLiveData<>();
    public final LiveData<ApiResult<ArrayList<ItemVO>>> itemList = _itemList;
    private final GithubRepository githubRepository;
    private final CompositeDisposable compositeDisposable;

    @Inject
    public MainViewModel(GithubRepository githubRepository) {
        this.githubRepository = githubRepository;
        compositeDisposable = new CompositeDisposable();
    }

    @SuppressLint("CheckResult")
    public void searchRepositories(String query) {
        compositeDisposable.add(githubRepository.searchRepositories(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            Log.e("onNext", "!!");
                            list = (ArrayList<ItemVO>) result.getData().getItems();
                            _itemList.setValue(ApiResult.success(list));
                        },
                        error -> {
                            Log.e("onError", "!!");
                            _itemList.setValue(ApiResult.error(error));
                        },
                        () -> {
                            Log.e("onCompleted", "!!");
                        }
                ));
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
