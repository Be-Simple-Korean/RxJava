package com.example.rxjava.network.repository;

import android.util.Log;

import com.example.rxjava.network.ApiResult;
import com.example.rxjava.network.GithubService;
import com.example.rxjava.network.model.GithubResponse;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;

public class GithubRepositoryImpl implements GithubRepository {
    private final GithubService gitHubService;

    @Inject
    public GithubRepositoryImpl(GithubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @Override
    public Observable<ApiResult<GithubResponse>> searchRepositories(String q) {
        return gitHubService.searchRepositories(q)
                .map(ApiResult::success)
                .onErrorReturn(ApiResult::error);
    }
}
