package com.example.rxjava.network.repository;

import com.example.rxjava.network.ApiResult;
import com.example.rxjava.network.model.GithubResponse;

import io.reactivex.rxjava3.core.Observable;

public interface GithubRepository {

    Observable<ApiResult<GithubResponse>> searchRepositories(String q);
}
