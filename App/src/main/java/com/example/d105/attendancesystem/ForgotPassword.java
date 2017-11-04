package com.example.d105.attendancesystem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {
    private static final String TAG = ForgotPassword.class.getSimpleName();
    Toolbar toolbar;
    TextView title;
    Button forgot_password;
    EditText email_id;
    private static String url = null;
    private Boolean exit;
    private AlertDialogManager alert = new AlertDialogManager();
    private ConnectionDetector cd;
    private ProgressDialog pDialog;
    boolean isInternetPresent;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
    toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("");
        title=(TextView)findViewById(R.id.title);
        title.setText("Forgot Password");
        ImageView cancle =(ImageView)findViewById(R.id.cancel);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        forgot_password=(Button)findViewById(R.id.submit_btn);
        email_id=(EditText)findViewById(R.id.email_edit);

        // Login button Click Event
        forgot_password.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                email = email_id.getText().toString().trim();


                // Check for empty data in the form
                if (!email.isEmpty()) {
                    // login user
                    forgotpassword(email);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });


    }


    /**
     * function to verify forgotpassword details in mysql db
     * */
    private void forgotpassword(final String email) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    String res = jObj.getString("status");
                    String notification = jObj.getString("notification");

                    // Check for error node in json
                    if (res.equals("true")) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ForgotPassword.this);
                        alertDialogBuilder.setTitle("");
                        alertDialogBuilder
                                .setMessage(notification.toString())
                                .setCancelable(false)
                                .setNegativeButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                dialog.cancel();
                                                onBackPressed();
                                            }
                                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {
                        // Error in login. Get the error message
                        // String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                "Login Failed!!", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}