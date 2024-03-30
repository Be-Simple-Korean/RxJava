package com.example.rxjava.di;

import com.example.rxjava.network.GithubService;
import com.example.rxjava.network.repository.GithubRepository;
import com.example.rxjava.network.repository.GithubRepositoryImpl;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Provides
    @Singleton
    public String getBaseUrl() {
        return "https://api.github.com/";
    }

    @Provides
    @Singleton
    public GsonConverterFactory createGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    public Retrofit getRetrofit(String baseUrl, GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public GithubService getGithubService(Retrofit retrofit) {
        return retrofit.create(GithubService.class);
    }

    @Provides
    @Singleton
    public GithubRepository getGithubRepository(GithubService githubService){
        return new GithubRepositoryImpl(githubService);
    }
}
