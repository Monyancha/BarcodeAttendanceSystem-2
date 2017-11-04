package com.example.d105.attendancesystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class ManageActivity extends AppCompatActivity {

    private ListView lv_list;
    private SubjectAdapter data_adapter;
    private ArrayList<Subject> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        createObject();
    }
    private void createObject() {
        data_list=new ArrayList<>();
        setDummyData();
        lv_list=(ListView)findViewById(R.id.lv_list);
        data_adapter=new SubjectAdapter(this,data_list);
        lv_list.setAdapter(data_adapter);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setItemClickOperation(position);
            }
        });
    }

    private void setItemClickOperation(int position) {
        Subject subject=data_list.get(position);
        Log.d("sub_time==","=="+subject.getTime());
        Calendar calendar=Calendar.getInstance();
        long current_time_stamp=calendar.getTimeInMillis();
        long from_timestamp=0L;
        long to_timestamp=0L;
        ArrayList<DayModel> day_list= subject.getDay_model_list();
        String current_day=fetchCurrenDay();
        for (DayModel day :
                day_list) {
            Log.d("day==","=="+day.getDay()+"=="+current_day);
            if(day.getDay().equals(current_day))
            {
                Log.d("match_day==","=="+day.getDay()+"=="+current_day);
                TimeModel time_model=  day.getTimeModel();
                calendar=Calendar.getInstance();
                calendar.set(Calendar.HOUR,time_model.getFrom_time_hr());
                Log.d("from_hr","=="+time_model.getFrom_time_hr());
                Log.d("from_min","=="+time_model.getFrom_time_min());
                calendar.set(Calendar.MINUTE,time_model.getFrom_time_min());
                if(time_model.getFrom_am_pm().equals("am"))
                    calendar.set(Calendar.AM_PM,Calendar.AM);
                else
                    calendar.set(Calendar.AM_PM,Calendar.PM);
                Log.d("from_Date","=="+calendar.getTime());
                from_timestamp=calendar.getTimeInMillis();
                Log.d("to_hr","=="+time_model.getTo_time_hr());
                Log.d("to_min","=="+time_model.getTo_time_min());
                calendar=Calendar.getInstance();
                calendar.set(Calendar.HOUR,time_model.getTo_time_hr());
                calendar.set(Calendar.MINUTE,time_model.getTo_time_min());
                if(time_model.getTo_am_pm().equals("am"))
                    calendar.set(Calendar.AM_PM,Calendar.AM);
                else
                    calendar.set(Calendar.AM_PM,Calendar.PM);
                Log.d("TO_Date","=="+calendar.getTime());
                to_timestamp=calendar.getTimeInMillis();
                break;
            }
        }
        if(from_timestamp>0 && to_timestamp>0)
        {
            Log.d("from_time_Stamp","=="+from_timestamp);
            Log.d("to_timestamp","=="+to_timestamp);
            Log.d("current_time_stamp","=="+current_time_stamp);
            if(current_time_stamp>from_timestamp && current_time_stamp<to_timestamp)
            {
                Toast.makeText(this,"In time hold",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ManageActivity.this, Barcode_Activity.class);
             startActivity(intent);
                //Toast.makeText(this,"Subject="+subject.getSubject(),Toast.LENGTH_LONG).show();
                // Toast.makeText(this,"Day="+subject.getDay(),Toast.LENGTH_LONG).show();
                //Toast.makeText(this,"Time="+subject.getTime(),Toast.LENGTH_LONG).show();
            }else Toast.makeText(this,"In time not hold",Toast.LENGTH_LONG).show();
        }else Toast.makeText(this,"Today is not same day ",Toast.LENGTH_LONG).show();
    }
    private String fetchCurrenDay()
    {
        String day="";
        Calendar calendar=Calendar.getInstance();

        switch (calendar.get(Calendar.DAY_OF_WEEK))
        {
            case Calendar.SUNDAY:
                day="Sunday";
                break;
            case Calendar.MONDAY:
                day="Monday";
                break;
            case Calendar.TUESDAY:
                day="Tuesday";
                break;
            case Calendar.WEDNESDAY:
                day="Wednesday";
                break;
            case Calendar.THURSDAY:
                day="Thrusday";
                break;
            case Calendar.FRIDAY:
                day="Friday";
                break;
            case Calendar.SATURDAY:
                day="Saturday";
                break;

        }
        return day;
    }

    private void setDummyData() {
        /*For Android*/
        Subject subject=new Subject();
        subject.setSubject("algorithm");
        ArrayList<DayModel> day_list=new ArrayList<>();
        /*First Day*/
        DayModel dayModel=new DayModel();
        dayModel.setDay("Monday");
        TimeModel timeModel=new TimeModel();
        timeModel.setFrom_time_hr(1);
        timeModel.setFrom_time_min(30);
        timeModel.setFrom_am_pm("pm");
        timeModel.setTo_time_hr(2);
        timeModel.setTo_time_min(45);
        timeModel.setTo_am_pm("pm");
        dayModel.setTimeModel(timeModel);
        day_list.add(dayModel);
        /*Second Day*/
        dayModel=new DayModel();
        dayModel.setDay("Tuesday");
        timeModel=new TimeModel();
        timeModel.setFrom_time_hr(3);
        timeModel.setFrom_time_min(00);
        timeModel.setFrom_am_pm("pm");
        timeModel.setTo_time_hr(4);
        timeModel.setTo_time_min(45);
        timeModel.setTo_am_pm("pm");
        dayModel.setTimeModel(timeModel);
        day_list.add(dayModel);

        /*End Second day*/


         /*third day */
        dayModel=new DayModel();
        dayModel.setDay("Friday");
        timeModel=new TimeModel();
        timeModel.setFrom_time_hr(9);
        timeModel.setFrom_time_min(30);
        timeModel.setFrom_am_pm("am");
        timeModel.setTo_time_hr(11);
        timeModel.setTo_time_min(00);
        timeModel.setTo_am_pm("am");
        dayModel.setTimeModel(timeModel);
        day_list.add(dayModel);

        /*End third day*/

        subject.setDay_model_list(day_list);
        data_list.add(subject);

        /*End For Android*/


        /*For Computer network*/
        subject=new Subject();
        subject.setSubject("Computer network");
        day_list=new ArrayList<>();
        /*First Day*/
        dayModel=new DayModel();
        dayModel.setDay("Tuesday");
        timeModel=new TimeModel();
        timeModel.setFrom_time_hr(9);//9
        timeModel.setFrom_time_min(30);//30
        timeModel.setFrom_am_pm("am");
        timeModel.setTo_time_hr(11);//11
        timeModel.setTo_time_min(00);//00
        timeModel.setTo_am_pm("am");
        dayModel.setTimeModel(timeModel);
        day_list.add(dayModel);
     /*End First day*/

       /*  *//*Second Day*//*
        dayModel=new DayModel();
        dayModel.setDay("Tuesday");
        timeModel=new TimeModel();
        timeModel.setFrom_time_hr(9);
        timeModel.setFrom_time_min(30);
        timeModel.setFrom_am_pm("am");
        timeModel.setTo_time_hr(11);
        timeModel.setTo_time_min(00);
        timeModel.setTo_am_pm("am");
        dayModel.setTimeModel(timeModel);
        day_list.add(dayModel);
     *//*End Second day*/

        subject.setDay_model_list(day_list);
        data_list.add(subject);
        /*End For Computer network*/

          /*For Database managment*/
        subject=new Subject();
        subject.setSubject("Database managment");
        day_list=new ArrayList<>();
        /*First Day*/
        dayModel=new DayModel();
        dayModel.setDay("Wednesday");
        timeModel=new TimeModel();
        timeModel.setFrom_time_hr(11);
        timeModel.setFrom_time_min(00);
        timeModel.setFrom_am_pm("am");
        timeModel.setTo_time_hr(12);
        timeModel.setTo_time_min(30);
        timeModel.setTo_am_pm("am");
        dayModel.setTimeModel(timeModel);
        day_list.add(dayModel);
        /*End Frist Day*/
       /* /*//*Second Day*//**//*
        dayModel=new DayModel();
        dayModel.setDay("Thrusday");
        timeModel=new TimeModel();
        timeModel.setFrom_time_hr(9);
        timeModel.setFrom_time_min(30);
        timeModel.setFrom_am_pm("am");
        timeModel.setTo_time_hr(10);
        timeModel.setTo_time_min(30);
        timeModel.setTo_am_pm("am");
        dayModel.setTimeModel(timeModel);
        day_list.add(dayModel);

*//*End Second Day*//*
        /*//*Third Day Day*//**//*
        dayModel=new DayModel();
        dayModel.setDay("Tuesday");
        timeModel=new TimeModel();
        timeModel.setFrom_time_hr(9);
        timeModel.setFrom_time_min(30);
        timeModel.setFrom_am_pm("am");
        timeModel.setTo_time_hr(10);
        timeModel.setTo_time_min(30);
        timeModel.setTo_am_pm("am");
        dayModel.setTimeModel(timeModel);
        day_list.add(dayModel);

        *//*End Third Day*/
        subject.setDay_model_list(day_list);
        data_list.add(subject);
        /*End For Database managment*/



          /*For Mathematic*/
        subject=new Subject();
        subject.setSubject("Mathematic");
        day_list=new ArrayList<>();
        /*First Day*/
        dayModel=new DayModel();
        dayModel.setDay("Monday");
        timeModel=new TimeModel();
        timeModel.setFrom_time_hr(11);
        timeModel.setFrom_time_min(00);
        timeModel.setFrom_am_pm("am");
        timeModel.setTo_time_hr(12);
        timeModel.setTo_time_min(30);
        timeModel.setTo_am_pm("am");
        dayModel.setTimeModel(timeModel);
        day_list.add(dayModel);
        /*End Frist Day*/
      //*Second Day*//**//*
        dayModel=new DayModel();
        dayModel.setDay("Wednesday");
        timeModel=new TimeModel();
        timeModel.setFrom_time_hr(1);
        timeModel.setFrom_time_min(30);
        timeModel.setFrom_am_pm("am");
        timeModel.setTo_time_hr(2);
        timeModel.setTo_time_min(45);
        timeModel.setTo_am_pm("am");
        dayModel.setTimeModel(timeModel);
        day_list.add(dayModel);

/*End Second Day*/
        /*Third Day Day*/
        dayModel=new DayModel();
        dayModel.setDay("Friday");
        timeModel=new TimeModel();
        timeModel.setFrom_time_hr(11);
        timeModel.setFrom_time_min(00);
        timeModel.setFrom_am_pm("am");
        timeModel.setTo_time_hr(12);
        timeModel.setTo_time_min(30);
        timeModel.setTo_am_pm("am");
        dayModel.setTimeModel(timeModel);
        day_list.add(dayModel);

        /*End Third Day*/
        subject.setDay_model_list(day_list);
        data_list.add(subject);
        /*End For Mathematic*/
    }
}