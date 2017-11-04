package com.example.d105.attendancesystem;

/**
 * Created by Krishan on 11-04-2017.
 */

public class TimeModel {
    private int from_time_hr;
    private int from_time_min;
    private int to_time_hr;
    private int to_time_min;
    private String from_am_pm="";
    private String to_am_pm="";

    public int getFrom_time_hr() {
        return from_time_hr;
    }

    public void setFrom_time_hr(int from_time_hr) {
        this.from_time_hr = from_time_hr;
    }

    public int getFrom_time_min() {
        return from_time_min;
    }

    public void setFrom_time_min(int from_time_min) {
        this.from_time_min = from_time_min;
    }

    public int getTo_time_hr() {
        return to_time_hr;
    }

    public void setTo_time_hr(int to_time_hr) {
        this.to_time_hr = to_time_hr;
    }

    public int getTo_time_min() {
        return to_time_min;
    }

    public void setTo_time_min(int to_time_min) {
        this.to_time_min = to_time_min;
    }

    public String getFrom_am_pm() {
        return from_am_pm;
    }

    public void setFrom_am_pm(String from_am_pm) {
        this.from_am_pm = from_am_pm;
    }

    public String getTo_am_pm() {
        return to_am_pm;
    }

    public void setTo_am_pm(String to_am_pm) {
        this.to_am_pm = to_am_pm;
    }
}
