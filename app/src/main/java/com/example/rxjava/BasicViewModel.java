package com.example.rxjava;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BasicViewModel extends ViewModel {
    private final MutableLiveData<String> _myData = new MutableLiveData<String>();
    public final LiveData<String> myData = _myData;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void loadData() {
        compositeDisposable.add(
                fetchDataFromNetWork()
                        .map(new Function<String, Object>() { // 아이템을 변환시 사용,
                            @Override
                            public Object apply(String s) throws Throwable {
                                return s.toUpperCase();
                            }
                        }).filter(new Predicate<Object>() {
                            @Override
                            public boolean test(Object o) throws Throwable {
                                return (o.toString()).startsWith("DATA");

                            }
                        })
                        .flatMap((Function<Object, ObservableSource<String>>) o -> Observable.just(o.toString() +"-Transfromed"))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((Consumer<String>) s -> {
                            Log.e("observable","onNext");
                            _myData.setValue(s);
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                Log.e("observable","onError - "+throwable.getMessage());
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Throwable {
                                Log.e("observable","onComplete");
                            }
                        })

        );
    }

    private Observable<String> fetchDataFromNetWork() {
        Log.e("viewmodel","fetchDataFromNetWork");
        return Observable.just("Data 1", "Data 2", "Data 3")
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io());
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
