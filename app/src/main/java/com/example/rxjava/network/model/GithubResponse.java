package com.example.rxjava.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GithubResponse {
    @SerializedName("items")
    private List<ItemVO> items;

    public List<ItemVO> getItems() {
        return items;
    }

    public void setItems(List<ItemVO> items) {
        this.items = items;
    }
}