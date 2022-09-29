package com.example.loveday.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loveday.database.MySharedPreferences;


public class UrlImageViewModel extends ViewModel {
    public MutableLiveData<String> urlImageBoy = new MutableLiveData<>();
    public MutableLiveData<String> urlImageGirl = new MutableLiveData<>();

    public void getUrlImage(Context context) {
        MySharedPreferences mySharedPreferences = new MySharedPreferences(context);
        urlImageBoy.setValue(mySharedPreferences.getImageBoy("urlImageBoy"));
        urlImageGirl.setValue(mySharedPreferences.getImageGirl("urlImageGirl"));
    }

}
