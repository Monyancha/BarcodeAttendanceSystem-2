package com.example.d105.attendancesystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TimeTableA extends AppCompatActivity {

    User_ListAdapter restaurantListAdapter;
    private ArrayList<Student> studentArrayList;
    TextView back,emmpity;
    ListView user_list;
    ArrayList<String> id_list;
    ArrayList<String> day_list;
    ArrayList<String> subject_list;
    ArrayList<String> time_array_algorithm;
    ArrayList<String> time_array_Computer;
    ArrayList<String> time_array_Database;
    ArrayList<String> time_array_Mathematic;

    ArrayList<String> time_array_algorithm_time;
    ArrayList<String> time_array_Computer_time;
    ArrayList<String> time_array_Database_time;
    ArrayList<String> time_array_Mathematic_time;


    ArrayList<String> time_list;
    private AlertDialogManager alert = new AlertDialogManager();
    private ConnectionDetector cd;
    boolean isInternetPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        setTitle("");

        //setViewContent();
    }

    public void setViewContent(){
        studentArrayList =new ArrayList<Student>();
        day_list = new ArrayList<String>();

        day_list.add("Monday");
        day_list.add("Tuesday");
        day_list.add("Wednesday");
        day_list.add("Thursday");
        day_list.add("Friday");
        Log.d("ssssssss",String.valueOf(day_list));
        subject_list = new ArrayList<String>();
        subject_list.add("algorithm");
        subject_list.add("Computer network");
        subject_list.add("Database managment");
        subject_list.add("Mathematic");
        time_array_algorithm = new ArrayList<String>();
        time_array_Computer = new ArrayList<String>();
        time_array_Database = new ArrayList<String>();
        time_array_Mathematic = new ArrayList<String>();

        time_array_algorithm_time = new ArrayList<String>();
        time_array_Computer_time = new ArrayList<String>();
        time_array_Database_time = new ArrayList<String>();
        time_array_Mathematic_time = new ArrayList<String>();



        time_list = new ArrayList<String>();
        time_list.add("09:30:00-11:00:00");
        time_list.add("11:00:00-12:30:00");
        time_list.add("01:30:00-02:45:00");
        time_list.add("03:00:00-04:45:00");
        // 1:40:16 PM





        for(int i=0;i<subject_list.size();i++) {
            if (subject_list.get(i).equals("algorithm")) {

                time_array_algorithm.add(0,day_list.get(0).toString());
                time_array_algorithm.add(1, day_list.get(1).toString());
                time_array_algorithm.add(2, day_list.get(4).toString());
                time_array_algorithm_time.add(0, time_list.get(2).toString());
                time_array_algorithm_time.add(1, time_list.get(3).toString());
                time_array_algorithm_time.add(2, time_list.get(0).toString());
            } else if (subject_list.get(i).equals("Computer network")) {

                time_array_Computer.add(0, day_list.get(1).toString());
                time_array_Computer_time.add(0, time_list.get(0).toString());
            } else if (subject_list.get(i).equals("Database managment")) {

                time_array_Database.add(0, day_list.get(2).toString());
                time_array_Database_time.add(0, time_list.get(1).toString());
            } else if (subject_list.get(i).equals("Mathematic")) {

                time_array_Mathematic.add(0, day_list.get(0).toString());
                time_array_Mathematic.add(1, day_list.get(2).toString());
                time_array_Mathematic.add(2, day_list.get(4).toString());

                time_array_Mathematic_time.add(0, time_list.get(1).toString());
                time_array_Mathematic_time.add(1, time_list.get(2).toString());
                time_array_Mathematic_time.add(2, time_list.get(1).toString());

            }
        }



        for(int i=0;i<subject_list.size();i++)
        {
            Student studs=new Student();
            studs.setsubject_list(subject_list.get(i).toString());
            //studs.settime_list(time_list.get(i).toString());
            if (subject_list.get(i).equals("algorithm")) {
                studs.setday_list(time_array_algorithm);
                studs.settime_list(time_array_algorithm_time);
            }
            else if (subject_list.get(i).equals("Computer network")) {
                studs.setday_list(time_array_Computer);
                studs.settime_list(time_array_Computer_time);
            }
            else if (subject_list.get(i).equals("Database managment")) {
                studs.setday_list(time_array_Database);
                studs.settime_list(time_array_Database_time);
            }
            else if (subject_list.get(i).equals("Mathematic")) {
                studs.setday_list(time_array_Mathematic);
                studs.settime_list(time_array_Mathematic_time);
            }


            studentArrayList.add(studs);

        }




        emmpity=(TextView)findViewById(R.id.list_empty);
        back=(TextView)findViewById(R.id.back);
        user_list=(ListView)findViewById(R.id.user_list);
        LayoutInflater inflater_grid = getLayoutInflater();
        ViewGroup header_grid = (ViewGroup) inflater_grid.inflate(R.layout.footer, user_list,
                false);
        ViewGroup footer_grid = (ViewGroup) inflater_grid.inflate(R.layout.footer, user_list,
                false);

        user_list.addHeaderView(header_grid, null, false);
        user_list.addFooterView(footer_grid, null, false);
        user_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String  record=parent.getItemAtPosition(position).toString();
                Intent intent=new Intent(TimeTableA.this, Barcode_Activity.class);
                intent.putExtra("rt_id",studentArrayList.get(position-1).getsubject_list());
                // Log.d("rt_id",rt_id);
                startActivity(intent);
            }
        });
        for(int i=0;i<studentArrayList.size();i++)
        {
            Log.d("studenyhhjjjj",studentArrayList.get(0).getsubject_list().toString());
        }

        restaurantListAdapter =new User_ListAdapter(this, studentArrayList);
        user_list.setAdapter(restaurantListAdapter);


     /*   studentArrayList.add(new Student(subject_list,day_list,image_url,time_list));

    restaurantListAdapter =new User_ListAdapter(getContext(), studentArrayList);
                    user_list.setAdapter(restaurantListAdapter);*/


        cd = new ConnectionDetector(this);
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            //  new user_listP().execute();
        } else {
            alert.showAlertDialog(this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }
    }
}
