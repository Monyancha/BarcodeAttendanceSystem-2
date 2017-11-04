package com.example.d105.attendancesystem;

import java.util.ArrayList;

/**
 * Created by Krishan on 11-04-2017.
 */

public class Subject {
    private String subject;
    private ArrayList<DayModel> day_model_list;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ArrayList<DayModel> getDay_model_list() {
        return day_model_list;
    }

    public void setDay_model_list(ArrayList<DayModel> day_model_list) {
        this.day_model_list = day_model_list;
    }

    public String getDay() {
        String day_name="";
        if(day_model_list!=null && day_model_list.size()>0)
        {
            for (DayModel day_ :
                    day_model_list) {
                String day=day_.getDay();
                day_name = day_name == "" ? day : day_name + "," + day;
            }
        }
        return day_name;
    }



    public String getTime() {
        String time_name="";
        if(day_model_list!=null && day_model_list.size()>0)
        {
            for (DayModel day_ :
                    day_model_list) {
                TimeModel time=day_.getTimeModel();
                if(time_name.equals(""))
                {
                    time_name=time.getFrom_time_hr()+":"+time.getFrom_time_min()+" "+time.getFrom_am_pm()+" to "+time.getTo_time_hr()+":"+time.getTo_time_min()+" "+time.getTo_am_pm();
                }else {
                    time_name=time_name+","+time.getFrom_time_hr()+":"+time.getFrom_time_min()+" "+time.getFrom_am_pm()+" to "+time.getTo_time_hr()+":"+time.getTo_time_min()+" "+time.getTo_am_pm();
                }
            }
        }
        return time_name;
    }


}
