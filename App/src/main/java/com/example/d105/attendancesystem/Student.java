package com.example.d105.attendancesystem;

import java.util.ArrayList;

/**
 * Created by D105 on 4/8/2017.
 */

public class Student {
    //private String rt_id;

    private String subject_list;
    private ArrayList<String> time_list;
    private String image_url;
    private String revie;
  //  private String time_list;
    ArrayList<String> day_list;

    Integer review_cou;

    public  Student()
    {

    }
    public Student(String subject_list, ArrayList<String> day_list, String image_url,  ArrayList<String> time_list){
        //this.rt_id = rt_id;
        this.subject_list = subject_list;
        this.day_list = day_list;
        this.image_url = image_url;
        this.time_list = time_list;
        this.revie = revie;

    }


    public String getimage_url(){return image_url;}




    public void setday_list(ArrayList<String> day_list) {
        this.day_list = day_list;
    }
    public ArrayList<String> getday_list(){
        return day_list;
    }


    public void settime_list(ArrayList<String> time_list) {
        this.time_list = time_list;
    }
    public ArrayList<String> gettime_list(){
        return time_list;
    }

    public void setsubject_list(String subject_list) {
        this.subject_list = subject_list;
    }
    public String getsubject_list(){
        return subject_list;
    }


}