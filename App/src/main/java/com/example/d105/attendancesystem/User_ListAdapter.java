package com.example.d105.attendancesystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by D105 on 4/8/2017.
 */

public class User_ListAdapter  extends BaseAdapter implements Filterable {
    Context context;
    String localTime;
    String dayOfTheWeek;
    Date datestring;
    private PopupWindow pwindo;
    private Boolean exit;


    private LayoutInflater inflater;
    public ArrayList<Student> restaurantArrayList;
    public ArrayList<Student> orig;
    TextView teacher_name;
    TextView restaurantname_text;
    TextView restaurant_items_text;
    TextView review_text;
    TextView price_text;
    TextView deliver_time_text;
    RoundedImageView rest_logo;
    RatingBar rate_coun;
    TextView open_close;
    String hrr,minn;
    String day_type;
    Date dateCompareOne1,dateCompareTwo1;
    String time_type;
    TextView delivery_fee;
    ImageView restaurant_type;
    public static final String inputFormat = "hh:mm";
    private static final String TAG = "Panoramio";
    private static final int IO_BUFFER_SIZE = 4 * 1024;
    SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat);
    public User_ListAdapter(Context context, ArrayList<Student> restaurantArrayList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.restaurantArrayList = restaurantArrayList;
        inflater		=	LayoutInflater.from(context);
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected android.widget.Filter.FilterResults performFiltering(CharSequence constraint) {
                final android.widget.Filter.FilterResults oReturn = new android.widget.Filter.FilterResults();
                final ArrayList<Student> results = new ArrayList<Student>();
                if (orig == null)
                    orig = restaurantArrayList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Student g : orig) {
                            if (g.getsubject_list().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                restaurantArrayList = (ArrayList<Student>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return restaurantArrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return restaurantArrayList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View V, ViewGroup parent) {
        // TODO Auto-generated method stub
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        V   = inflater.inflate(R.layout.user_list_row, null);
        /*Typeface face = Typeface.createFromAsset(context.getAssets(),
                "robot_font/Roboto-Bold.ttf");*/
        rest_logo = (RoundedImageView) V.findViewById(R.id.logo);
        restaurant_items_text = (TextView) V.findViewById(R.id.student_name);
        restaurantname_text = (TextView) V.findViewById(R.id.teacher_name);
        deliver_time_text = (TextView) V.findViewById(R.id.time);

        Log.d("yyyyyyy",String.valueOf(restaurantArrayList));
        final   Student student=restaurantArrayList.get(position);

        restaurantname_text.setText(student.getsubject_list());

        day_type="";
            for (int i = 0; i < student.getday_list().size(); i++) {
                if (day_type == null) {
                    day_type = student.getday_list().get(i);
                } else {
                    day_type = day_type + "," + student.getday_list().get(i);
                }

                //restaurant_items_text.setText(student.getday_list());
                Log.d("sdafdaf", student.getday_list().get(i).toString());
            }
            restaurant_items_text.setText(day_type);


        time_type="";
        for (int i = 0; i < student.gettime_list().size(); i++) {
                if (time_type == null) {
                    time_type = student.gettime_list().get(i);
                } else {
                    time_type = time_type + "," + student.gettime_list().get(i);
                }

            //restaurant_items_text.setText(student.getday_list());
            Log.d("sdafdaf", student.gettime_list().get(i).toString());
        }

        deliver_time_text.setText(time_type);


  /*      Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
// you can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));

        String localTime = date.format(currentLocalTime);*/


        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-4:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("KK:mm a");
        date.setTimeZone(TimeZone.getTimeZone("GMT-4:00"));
       localTime = date.format(currentLocalTime);
        Log.d("Current Time",localTime);




        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        dayOfTheWeek = sdf.format(d);

        Log.d("Current day",dayOfTheWeek);
        V.setTag(position);
        V.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tag = (int)v.getTag();

               // String str=day_type;
               // Log.d("dayyyyyy",str);
                ArrayList aList= restaurantArrayList.get(tag).getday_list();
                String subjctName = restaurantArrayList.get(tag).getsubject_list();
                for(int i=0;i<aList.size();i++)
                {
                    Log.d("gggghj",aList.get(i).toString());
                    String day_of_curent=aList.get(i).toString();

                        int indx=i;

                    if(day_of_curent.equals(dayOfTheWeek)) {



                        //String str1=time_type;
                        // str1="10:00"+str1;
                        // Log.d("GUY",str1);

                        ArrayList aList1 = restaurantArrayList.get(position).gettime_list();


                        Log.d("dfbjdbgfdg", aList1.get(indx).toString());

                      /*  for (int j = 0; j < aList1.size(); j++) {*/
                            // Log.d("mytymmmm",aList.get(j).toString());
                            String time_of_curent = aList1.get(indx).toString();
                            Log.d("iop", time_of_curent);
                            //123dance456
                            String s = time_of_curent;

                            List<String> timeList = Arrays.asList(s.split("-"));
                            //String[] split = s.split("-");
                            String firstSubString = timeList.get(0).toString();
                            //String secondSubString = split[1];
                            Log.d("firstSubString", firstSubString);

                            String secondSubString = timeList.get(1).toString();
                            //String secondSubString = split[1];
                            Log.d("secSubString", secondSubString);
                            //Log.d("secondSubString", secondSubString);
                           /* String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                            //Date d = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                            Date date = new Date();
                            //ContentValues initialValues = new ContentValues();
                            //initialValues.put("date_created", dateFormat.format(date));
                            //long rowId = mDb.insert(DATABASE_TABLE, null, initialValues);
                            Log.d("yui", dateFormat.format(date));
                            String ghu=dateFormat.format(date);

                           // Toast.makeText(context, "Json error: " + "Today time out ", Toast.LENGTH_LONG).show();
//                            if(dateFormat.format(date)>=firstSubString && dateFormat.format(date)<=end_string)
//                                 {
//                                     Log.d("true", "true");
//                                 }
//                                 else
//                                 {
//                                     Log.d("false", "false");
//                                 }*/

                            Calendar now = Calendar.getInstance();
                            int hour = now.get(Calendar.HOUR);
                            int minute = now.get(Calendar.MINUTE);

                           // datestring = parseDate("0"+hour + ":" + "0"+minute + ":" +"00");

                            if(hour>=0 && hour<=9)
                            {
                                 hrr="0"+hour;
                            }
                            else
                            {
                                hrr=String.valueOf(hour);
                            }


                            if(minute>=0 && minute<=9)
                            {
                                minn="0"+minute;
                            }
                            else
                            {
                                minn=String.valueOf(minute);
                            }

                            String curtimestring=(hrr + ":" + minn + ":" +"00");

                            dateCompareOne1 = parseDate(firstSubString);
                            String fs=firstSubString;
                            dateCompareTwo1 = parseDate(secondSubString);
                            String sc=secondSubString;
                            /*if (dateCompareOne1.before(datestring) && dateCompareTwo1.after(datestring)) { //yada yada }



                                    Intent intent = new Intent(context, Barcode_Activity.class);
                                    context.startActivity(intent);

                            }
                            else
                            {
                                Toast.makeText(context, "Your Time is out", Toast.LENGTH_LONG).show();
                            }*/
                            try
                            {

                               if(isTimeBetweenTwoTime(fs,sc,curtimestring))
                                {
                                    Intent intent = new Intent(context, Barcode_Activity.class);
                                    intent.putExtra("SubName", subjctName);
                                    context.startActivity(intent);
                                }

                                else
                                {
                                    new AlertDialog.Builder(context)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setTitle("Error Message")
                                            .setMessage("Your class time is out")
                                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    exit = true;
                                                    new Handler().postDelayed(new Runnable() {

                                                        @Override
                                                        public void run() {
                                                            // TODO Auto-generated method stub
                                                           /* Intent a = new Intent(Intent.ACTION_MAIN);
                                                            a.addCategory(Intent.CATEGORY_HOME);
                                                            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            startActivity(a);*/
                                                        }
                                                    }, 000);
                                                }
                                            }).setNegativeButton("no", null).show();

                                   // Toast.makeText(context, "Your Time is out", Toast.LENGTH_LONG).show();
                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                      //  }
                    }
                    else
                    {
                        //Toast.makeText(context, "Day is not matched", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        return V;
    }

/*    private Date parseDate(String date) { try { return inputParser.parse(date); } catch (java.text.ParseException e) { return new Date(0); }
    public static final String inputFormat = "HH:mm";

    private Date date;
    private Date dateCompareOne;
    private Date dateCompareTwo;

    private String compareStringOne = "9:45";
    private String compareStringTwo = "1:45";

    SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);

    private void compareDates(){
        Calendar now = Calendar.getInstance();

        int hour = now.get(Calendar.HOUR);
        int minute = now.get(Calendar.MINUTE);

        date = parseDate(hour + ":" + minute);
        dateCompareOne = parseDate(compareStringOne);
        dateCompareTwo = parseDate(compareStringTwo);

        if ( dateCompareOne.before( date ) && dateCompareTwo.after(date)) {
            //yada yada
        }
    }*/

    private Date parseDate(String date) {

        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }
    public static boolean isTimeBetweenTwoTime(String st, String endt, String currentTime) throws ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        if (st.matches(reg) && endt.matches(reg) && currentTime.matches(reg)) {
            boolean valid = false;
//Start Time
            java.util.Date inTime = new SimpleDateFormat("HH:mm:ss").parse(st);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(inTime);
//Current Time
            java.util.Date checkTime = new SimpleDateFormat("HH:mm:ss").parse(currentTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(checkTime);
//End Time
            java.util.Date finTime = new SimpleDateFormat("HH:mm:ss").parse(endt);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(finTime);
            if (endt.compareTo(st) < 0) {
                calendar2.add(Calendar.DATE, 1);
                calendar3.add(Calendar.DATE, 1);
            }
            java.util.Date actualTime = calendar3.getTime();
            if ((actualTime.after(calendar1.getTime()) || actualTime.compareTo(calendar1.getTime()) == 0)
                    && actualTime.before(calendar2.getTime())) {
                valid = true;
            }
            return valid;
        } else {
            throw new IllegalArgumentException("Not a valid time, expecting HH:MM:SS format");
        }
    }



}