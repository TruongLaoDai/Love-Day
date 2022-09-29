package com.example.loveday.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loveday.database.MySharedPreferences;
import com.example.loveday.model.SetUp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MainViewModel extends ViewModel {
    public MutableLiveData<String> boyName = new MutableLiveData<>();
    public MutableLiveData<String> boyAge = new MutableLiveData<>();
    public MutableLiveData<String> girlName = new MutableLiveData<>();
    public MutableLiveData<String> girlAge = new MutableLiveData<>();
    public MutableLiveData<Long> dayNumber = new MutableLiveData<>();

    public void callData(@NonNull SetUp setUp) {
        boyName.setValue(setUp.getBoyName());
        boyAge.setValue(setUp.getBoyAge());
        girlAge.setValue(setUp.getGirlAge());
        girlName.setValue(setUp.getGirlName());
        dayNumber.setValue(setUp.getTotalDay());

    }

    // method get data from database
    public void getValues(Context context) {
        MySharedPreferences mySharedPreferences = new MySharedPreferences(context);
        boyName.setValue(mySharedPreferences.getBoyNameValue("boyName"));
        boyAge.setValue(mySharedPreferences.getBoyAgeValue("boyAge"));
        girlName.setValue(mySharedPreferences.getGirlNameValue("girlName"));
        girlAge.setValue(mySharedPreferences.getGirlAgeValue("girlAge"));
        String sDay = mySharedPreferences.getLoveDay("loveDay");
        String sMonth = mySharedPreferences.getLoveMonth("loveMonth");
        String sYear = mySharedPreferences.getLoveYear("loveYear");
        Calendar c = Calendar.getInstance();
        String eDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String eMonth = String.valueOf(c.get(Calendar.MONTH) + 1);
        String eYear = String.valueOf(c.get(Calendar.YEAR));
        String start = sDay + "/" + sMonth + "/" + sYear;
        String end = eDay + "/" + eMonth + "/" + eYear;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date startDate = simpleDateFormat.parse(start);
            Date endDate = simpleDateFormat.parse(end);
            long startValue = Objects.requireNonNull(startDate).getTime();
            long endValue = Objects.requireNonNull(endDate).getTime();
            long tmp = Math.abs(startValue - endValue);
            dayNumber.setValue(tmp / (24 * 60 * 60 * 1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}