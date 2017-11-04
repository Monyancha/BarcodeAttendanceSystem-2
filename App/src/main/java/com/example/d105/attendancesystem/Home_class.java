package com.example.d105.attendancesystem;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by D105 on 4/8/2017.
 */

public class Home_class extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_screen, null);



      /*  Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-4:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("KK:mm");
        date.setTimeZone(TimeZone.getTimeZone("GMT-4:00"));
        String localTime = date.format(currentLocalTime);
        Log.d("Current Time",localTime);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        Log.d("Current day",dayOfTheWeek);*/



// textView is the TextView view that should display it
        //textView.setText(currentDateTimeString);

        studentArrayList =new ArrayList<Student>();
       // id_list = new ArrayList<String>();
        day_list = new ArrayList<String>();
   /*     day_list.add("MON");
        day_list.add("TUE");
        day_list.add("WED");
        day_list.add("THU");
        day_list.add("FRI");*/

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




        emmpity=(TextView)view.findViewById(R.id.list_empty);
        back=(TextView)view.findViewById(R.id.back);
        user_list=(ListView)view.findViewById(R.id.user_list);
        LayoutInflater inflater_grid = getActivity().getLayoutInflater();
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
                Intent intent=new Intent(getContext(),Barcode_Activity.class);
                intent.putExtra("rt_id",studentArrayList.get(position-1).getsubject_list());
              // Log.d("rt_id",rt_id);
                startActivity(intent);
            }
        });
for(int i=0;i<studentArrayList.size();i++)
{
    Log.d("studenyhhjjjj",studentArrayList.get(0).getsubject_list().toString());
}

        restaurantListAdapter =new User_ListAdapter(getContext(), studentArrayList);
        user_list.setAdapter(restaurantListAdapter);


     /*   studentArrayList.add(new Student(subject_list,day_list,image_url,time_list));

    restaurantListAdapter =new User_ListAdapter(getContext(), studentArrayList);
                    user_list.setAdapter(restaurantListAdapter);*/


        cd = new ConnectionDetector(getContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
          //  new user_listP().execute();
        } else {
            alert.showAlertDialog(getContext(), "No Internet Connection",
                    "You don't have internet connection.", false);
        }


        return  view;
    }



    class user_listP extends AsyncTask<String, String, String> {
        String response = "";
        ProgressDialog pDialog;
        String charset = "UTF-8";
        String responseFinal = "", json_str = "";
        String message;
        int result_value;
        String requestURL = "http://bhukaddd.com/webservices/location-related-restaurant-webservice.php?city_id=728&area_id=2519";
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("wait ...");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
           // pDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.layout_process_bar));
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(requestURL);
            try {
                List<NameValuePair> value = new ArrayList<NameValuePair>();
                /*value.add(new BasicNameValuePair("username", user_nameString));
				value.add(new BasicNameValuePair("password",
						user_passwordString));*/
                post.setEntity(new UrlEncodedFormEntity(value));
                HttpResponse response = client.execute(post);
                HttpEntity entity = response.getEntity();
                json_str = EntityUtils.toString(entity);
            } catch (IOException ex) {
                Log.d("hii", ex.toString());
            }
            return json_str;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            if (result != null) {

                try {
                    Log.d("result", result);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray user = jsonObject.getJSONArray("results");
                    for(int i = 0 ; i < user.length();i++)
                    {
                        JSONObject  jsondata = user.getJSONObject(i);
                        Log.d("result99999999999==", jsondata+"");
                        //studentArrayList.add(new Student(jsondata.getString("rt_id"),jsondata.getString("rt_name"), jsondata.getString("cat_name"),jsondata.getString("rt_image"),jsondata.getString("rt_estimated_delivery_time")));
                    }

                } catch (JSONException e) {
                    emmpity.setVisibility(View.VISIBLE);
                    back.setVisibility(View.VISIBLE);
                    emmpity.setText("Result not found.");
                    e.printStackTrace();
                }
            }
        }
    }
}