package com.example.d105.attendancesystem;

/**
 * Created by ilogics-dv1 on 6/4/17.
 */

public class CountryModelClass {
    private String id,code,con_name;
    private int year;
    private double rating;
    // private ArrayList<String> genre;

    public CountryModelClass() {
    }

    public CountryModelClass(String id, String code, String con_name) {
        this.id = id;
        this.code = code;
        this.con_name = con_name;



        //this.genre = genre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCon_name() {
        return con_name;
    }

    public void setCon_name(String con_name) {
        this.con_name = con_name;
    }


}

