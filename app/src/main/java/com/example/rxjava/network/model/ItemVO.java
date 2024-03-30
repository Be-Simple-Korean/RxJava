package com.example.rxjava.network.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class ItemVO {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "name = "+name;
    }
}
