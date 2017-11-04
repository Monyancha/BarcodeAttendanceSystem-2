package com.example.d105.attendancesystem;

/**
 * Created by Krishan on 11-04-2017.
 */

public class DayModel {
    private String day="";
    private TimeModel timeModel;

    public TimeModel getTimeModel() {
        return timeModel;
    }

    public void setTimeModel(TimeModel timeModel) {
        this.timeModel = timeModel;
    }

    public String getDay() {

        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
