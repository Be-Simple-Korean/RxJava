package com.example.rxjava.network;

import com.example.rxjava.network.model.GithubResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubService {
    @GET("search/repositories")
    Observable<GithubResponse> searchRepositories(@Query("q") String query);
}
