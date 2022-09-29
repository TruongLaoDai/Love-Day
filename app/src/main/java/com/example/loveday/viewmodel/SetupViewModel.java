package com.example.loveday.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loveday.database.MySharedPreferences;
import com.example.loveday.model.SetUp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class SetupViewModel extends ViewModel {
    public MutableLiveData<String> inputBoyName = new MutableLiveData<>();
    public MutableLiveData<String> inputBoyAge = new MutableLiveData<>();
    public MutableLiveData<String> inputGirlName = new MutableLiveData<>();
    public MutableLiveData<String> inputGirlAge = new MutableLiveData<>();
    public MutableLiveData<String> inputDay = new MutableLiveData<>();
    public MutableLiveData<String> inputMonth = new MutableLiveData<>();
    public MutableLiveData<String> inputYear = new MutableLiveData<>();

    public SetUp saveUser() {
        long gDayNumber = 0;

        // get data from edittext
        String bName = inputBoyName.getValue().trim();
        String bAge = inputBoyAge.getValue().trim();
        String gName = inputGirlName.getValue().trim();
        String gAge = inputGirlAge.getValue().trim();
        String sDay = inputDay.getValue().trim();
        String sMonth = inputMonth.getValue().trim();
        String sYear = inputYear.getValue().trim();

        // get current day, month, year
        Calendar c = Calendar.getInstance();
        String eDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String eMonth = String.valueOf(c.get(Calendar.MONTH) + 1);
        String eYear = String.valueOf(c.get(Calendar.YEAR));

        // calculate love day
        String start = sDay + "/" + sMonth + "/" + sYear;
        String end = eDay + "/" + eMonth + "/" + eYear;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date startDate = simpleDateFormat.parse(start);
            Date endDate = simpleDateFormat.parse(end);
            long startValue = Objects.requireNonNull(startDate).getTime();
            long endValue = Objects.requireNonNull(endDate).getTime();
            long tmp = endValue - startValue;
            gDayNumber = tmp / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SetUp(bName, bAge, gName, gAge, sDay, sMonth, sYear, gDayNumber);
    }

    public void saveDatabase(Context context) {
        MySharedPreferences mySharedPreferences = new MySharedPreferences(context);
        mySharedPreferences.putBoyNameValue("boyName", inputBoyName.getValue().trim());
        mySharedPreferences.putBoyAgeValue("boyAge", inputBoyAge.getValue().trim());
        mySharedPreferences.putGirlNameValue("girlName", inputGirlName.getValue().trim());
        mySharedPreferences.putGirlAgeValue("girlAge", inputGirlAge.getValue().trim());
        mySharedPreferences.putLoveDay("loveDay", inputDay.getValue().trim());
        mySharedPreferences.putLoveMonth("loveMonth", inputMonth.getValue().trim());
        mySharedPreferences.putLoveYear("loveYear", inputYear.getValue().trim());
    }

    // method get data from database
    public void getValues(Context context) {
        MySharedPreferences mySharedPreferences = new MySharedPreferences(context);
        inputBoyName.setValue(mySharedPreferences.getBoyNameValue("boyName"));
        inputBoyAge.setValue(mySharedPreferences.getBoyAgeValue("boyAge"));
        inputGirlName.setValue(mySharedPreferences.getGirlNameValue("girlName"));
        inputGirlAge.setValue(mySharedPreferences.getGirlAgeValue("girlAge"));
        inputDay.setValue(mySharedPreferences.getLoveDay("loveDay"));
        inputMonth.setValue(mySharedPreferences.getLoveMonth("loveMonth"));
        inputYear.setValue(mySharedPreferences.getLoveYear("loveYear"));
    }
}
