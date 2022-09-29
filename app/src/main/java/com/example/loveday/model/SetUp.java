package com.example.loveday.model;

import java.io.Serializable;

public class SetUp implements Serializable {
    private final String boyName;
    private final String boyAge;
    private final String girlName;
    private final String girlAge;
    private final String day;
    private final String month;
    private final String year;
    private final long totalDay;

    public SetUp(String boyName, String boyAge, String girlName, String girlAge, String day, String month, String year, long totalDay) {
        this.boyName = boyName;
        this.boyAge = boyAge;
        this.girlName = girlName;
        this.girlAge = girlAge;
        this.day = day;
        this.month = month;
        this.year = year;
        this.totalDay = totalDay;
    }

    public String getBoyName() {
        return boyName;
    }

    public String getBoyAge() {
        return boyAge;
    }

    public String getGirlName() {
        return girlName;
    }

    public String getGirlAge() {
        return girlAge;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public long getTotalDay() {
        return totalDay;
    }
}
