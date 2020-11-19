package com.souqelebel.models;

import android.content.Context;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.souqelebel.BR;
import com.souqelebel.R;

import java.io.Serializable;

public class ItemAddAds extends BaseObservable implements Serializable {
    private int id;
    private String title;
    private String icon;
    private String type;
    private String content="";


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public String getType() {
        return type;
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }
}
