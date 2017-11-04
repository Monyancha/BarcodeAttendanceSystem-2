package com.example.d105.attendancesystem;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {
    Toolbar toolbar;
    TextView title,edit_profile,personal_info,locainfo,user_full_name;
    AutoCompleteTextView first_name,last_name,email_id;
    AutoCompleteTextView mobile_number,city_name,state_name,country_name,gender_name,m_number;
    Spinner gender,country,state,city;
    String u_id,u_r_id,u_fb_id,f_name,l_name,full_name="Raj Sharma",e_mail,u_phone,u_gender,u_password,u_image_url="";
    private SessionManager session;
    private HashMap<String, String> user;
    private static String url = null,url_user_detail="";
    CircleImageView profile_image;
    public ImageView imageView;
    public Button   selectImage, uploadImage;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;
    String first_name_val="";
    String last_name_value="";
    private  AlertDialogManager alert = new AlertDialogManager();
    private ConnectionDetector cd;
    boolean isInternetPresent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
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
        title=(TextView)findViewById(R.id.title);
        title.setText("Profile details");
       /* Typeface face = Typeface.createFromAsset(getAssets(),
                "robot_font/Roboto-Bold.ttf");
        title.setTypeface(face);*/
        cd = new ConnectionDetector(UserProfile.this);
        isInternetPresent = cd.isConnectingToInternet();
        if(isInternetPresent)
        {
           /* session = new SessionManager(getApplicationContext());
            if (session.isLoggedIn()) {
                u_id=session.getUid().toString();
                u_r_id=session.getRegistrationId().toString();
                u_fb_id=session.getFbId().toString();
                f_name=session.getFirstName().toString();
                l_name=session.getLastName().toString();
                full_name=session.getFullName().toString();
                e_mail=session.getUserEmailId().toString();
                u_phone=session.getPhone().toString();
                u_password=session.getPassword().toString();
                u_gender=session.getGender().toString();
                u_image_url=session.getImageUrl().toString();
            }*/

        }else{
            alert.showAlertDialog(UserProfile.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }
        profile_image=(CircleImageView)findViewById(R.id.profile_image);
        if(isInternetPresent)
        {
            if (u_image_url.equals(""))
            {
                profile_image.setImageResource(R.drawable.default_profile);
            }
            else
            {
                Picasso.with(getApplicationContext())
                        .load(u_image_url)
                        .placeholder(R.drawable.default_profile)
                        .into(profile_image, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }
                            @Override
                            public void onError() {
                                profile_image.setImageResource(R.drawable.default_profile);
                            }
                        });
            }

        }else{
            alert.showAlertDialog(UserProfile.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }
        user_full_name=(TextView)findViewById(R.id.user_full_name);
        user_full_name.setText(full_name);
        first_name=(AutoCompleteTextView)findViewById(R.id.first_name);
        first_name.setText(f_name);
        last_name=(AutoCompleteTextView)findViewById(R.id.last_name);
        last_name.setText(l_name);
        email_id=(AutoCompleteTextView)findViewById(R.id.email_id);
        email_id.setText(e_mail);
        mobile_number=(AutoCompleteTextView)findViewById(R.id.mobile_number);
        mobile_number.setText(u_phone);
        m_number=(AutoCompleteTextView)findViewById(R.id.m_number);
        m_number.setText("+91");
        int position = first_name.length();
        Editable etext = first_name.getText();
        Selection.setSelection(etext, position);
        first_name.setEnabled(false);
        last_name.setEnabled(false);
        email_id.setEnabled(false);
        mobile_number.setEnabled(false);
        edit_profile=(TextView)findViewById(R.id.edit_profile);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_profile.getText().toString().equals("Edit"))
                {
                    first_name.setEnabled(true);
                    last_name.setEnabled(true);
                    email_id.setEnabled(true);
                    mobile_number.setEnabled(true);
                    edit_profile.setText("Apply");
                }
                else if(edit_profile.getText().toString().equals("Apply"))
                {
                    if(first_name.getText().toString().length()==0)
                    {
                        Toast.makeText(getApplicationContext(),"Please enter first name",Toast.LENGTH_LONG).show();
                    }
                    else if(last_name.getText().toString().length()==0)
                    {
                        Toast.makeText(getApplicationContext(),"Please enter last name",Toast.LENGTH_LONG).show();
                    }
                    else  if(email_id.getText().toString().length()==0)
                    {
                        Toast.makeText(getApplicationContext(),"Please enter email id",Toast.LENGTH_LONG).show();
                    }
                    else if(mobile_number.getText().toString().length()==0)
                    {
                        Toast.makeText(getApplicationContext(),"Please enter mobile number",Toast.LENGTH_LONG).show();
                    }
                    else {

                        try {
                            first_name_val = URLEncoder.encode(first_name.getText().toString(), "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        try {
                            last_name_value = URLEncoder.encode(last_name.getText().toString(), "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }


                        if(isInternetPresent)
                        {
                            //url = Constant.update_profile+u_id+"&user_first_name="+first_name_val.toString()+"&user_last_name="+last_name_value.toString()+"&user_email="+email_id.getText().toString()+"&user_phone="+mobile_number.getText().toString();
                            System.out.println(url);
                           // new updateProfile().execute();
                            first_name.setEnabled(false);
                            last_name.setEnabled(false);
                            email_id.setEnabled(false);
                            mobile_number.setEnabled(false);
                            edit_profile.setText("Edit");

                        }else{
                            alert.showAlertDialog(UserProfile.this, "No Internet Connection",
                                    "You don't have internet connection.", false);
                        }
                    }
                }
            }
        });
        personal_info=(TextView)findViewById(R.id.personal_info);
        /*edit_profile.setTypeface(face);
        personal_info.setTypeface(face);*/
        requestStoragePermission();
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        if(isInternetPresent)
        {
            //url_user_detail = Constant.get_user_profile+u_id;
            System.out.println(url_user_detail);
           // new userDetails().execute();
        }else{
            alert.showAlertDialog(UserProfile.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }

    }
    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_image.setImageBitmap(bitmap);
                //uploadMultipart();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }
    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        Log.d("path",path);
        return path;
    }
    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
    private class updateProfile extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UserProfile.this);
            pDialog.setMessage("wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {
            //ServiceHandler sh = new ServiceHandler();
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout((HttpParams)basicHttpParams, (int)50000);
            HttpConnectionParams.setSoTimeout((HttpParams)basicHttpParams, (int)50000);
            HttpConnectionParams.setTcpNoDelay((HttpParams)basicHttpParams, (boolean)true);
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient((HttpParams)basicHttpParams);
            HttpGet httpGet = new HttpGet(url);
            BasicResponseHandler basicResponseHandler = new BasicResponseHandler();
            String json=null;
            try {
                json = new String((String)defaultHttpClient.execute((HttpUriRequest)httpGet, (ResponseHandler)basicResponseHandler));

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }
        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            try {
                Log.d("result==", result+"");
                if (result!=null) {
                    JSONObject json = new JSONObject(result);
                    String message = json.getString("message");
                    String notification = json.getString("notification");
                    if (message.equals("ok")) {
                        user = new HashMap<String, String>();
                        /*user.put(SessionManager.KEY_UID, u_id.toString());
                        user.put(SessionManager.KEY_REGISTRATION_ID, u_r_id.toString());
                        user.put(SessionManager.KEY_FB_ID, u_fb_id.toString());
                        user.put(SessionManager.KEY_EMAIL_ID, email_id.getText().toString());
                        user.put(SessionManager.KEY_FIRST_NAME, first_name.getText().toString());
                        user.put(SessionManager.KEY_LAST_NAME, last_name.getText().toString());
                        user.put(SessionManager.KEY_FULL_NAME, first_name.getText().toString() + " " + last_name.getText().toString());
                        user.put(SessionManager.KEY_PASSWORD, u_password.toString());
                        user.put(SessionManager.KEY_PHONE, mobile_number.getText().toString());
                        user.put(SessionManager.KEY_GENDER, u_gender.toString());
                        user.put(SessionManager.KEY_IMAGE_URL, u_image_url.toString());
                        session.updateProfileData(user);*/
                        user_full_name.setText(first_name.getText().toString() + " " + last_name.getText().toString());
                        first_name.setEnabled(false);
                        last_name.setEnabled(false);
                        email_id.setEnabled(false);
                        mobile_number.setEnabled(false);
                        edit_profile.setText("Edit");
                        Toast.makeText(getApplicationContext(), notification.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private class userDetails extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UserProfile.this);
            pDialog.setMessage("wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {
            //ServiceHandler sh = new ServiceHandler();
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout((HttpParams)basicHttpParams, (int)50000);
            HttpConnectionParams.setSoTimeout((HttpParams)basicHttpParams, (int)50000);
            HttpConnectionParams.setTcpNoDelay((HttpParams)basicHttpParams, (boolean)true);
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient((HttpParams)basicHttpParams);
            HttpGet httpGet = new HttpGet(url_user_detail);
            BasicResponseHandler basicResponseHandler = new BasicResponseHandler();
            String json=null;
            try {
                json = new String((String)defaultHttpClient.execute((HttpUriRequest)httpGet, (ResponseHandler)basicResponseHandler));

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }
        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            try {
                Log.d("result==", result+"");
                if (result!=null) {
                    JSONObject json = new JSONObject(result);
                    String message = json.getString("message");
                    if (message.equals("ok")) {

                        JSONArray results=json.getJSONArray("results");

                        for (int i=0;i<results.length();i++){
                            JSONObject jsonObject=results.getJSONObject(i);

                            u_id=jsonObject.getString("user_id");
                            u_r_id=jsonObject.getString("user_registration_id");
                            u_fb_id=jsonObject.getString("user_fb_id");
                            e_mail=jsonObject.getString("user_email");
                            f_name=jsonObject.getString("user_first_name");
                            l_name=jsonObject.getString("user_last_name");
                            full_name = f_name.toString() + " " + l_name.toString();
                            u_password=jsonObject.getString("user_password");
                            u_phone=jsonObject.getString("user_phone");
                            u_gender=jsonObject.getString("user_gender");
                            u_image_url=jsonObject.getString("user_image");

                           /* user = new HashMap<String, String>();
                            user.put(SessionManager.KEY_UID, u_id.toString());
                            user.put(SessionManager.KEY_REGISTRATION_ID, u_r_id.toString());
                            user.put(SessionManager.KEY_FB_ID, u_fb_id.toString());
                            user.put(SessionManager.KEY_EMAIL_ID, e_mail.toString());
                            user.put(SessionManager.KEY_FIRST_NAME, f_name.toString());
                            user.put(SessionManager.KEY_LAST_NAME, l_name.toString());
                            user.put(SessionManager.KEY_FULL_NAME, f_name.toString() + " " + l_name.toString());
                            user.put(SessionManager.KEY_PASSWORD, u_password.toString());
                            user.put(SessionManager.KEY_PHONE, u_phone.toString());
                            user.put(SessionManager.KEY_GENDER, u_gender.toString());
                            user.put(SessionManager.KEY_IMAGE_URL, u_image_url.toString());
                            session.updateProfileData(user);*/
                            user_full_name.setText(f_name.toString() + " " + l_name.toString());
                            first_name.setText(f_name);
                            first_name.setEnabled(false);
                            last_name.setText(l_name);
                            last_name.setEnabled(false);
                            email_id.setText(e_mail);
                            email_id.setEnabled(false);
                            mobile_number.setText(u_phone);
                            mobile_number.setEnabled(false);
                            edit_profile.setText("Edit");

                        }

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
