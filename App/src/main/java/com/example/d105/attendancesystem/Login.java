package com.example.d105.attendancesystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by D105 on 4/7/2017.
 */

public class Login extends Activity {


    private static final String TAG = Login.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister,forgot_password;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    // private SQLiteHandler db;
    String email,password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        final RippleView rippleView = (RippleView) findViewById(R.id.more);
        inputEmail = (EditText) findViewById(R.id.email_edit);
        inputPassword = (EditText) findViewById(R.id.password_edit);
        btnLogin = (Button) findViewById(R.id.btnlogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnlinktoreg);
        forgot_password = (Button) findViewById(R.id.forgot_password);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        //db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
       /* if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }*/



        rippleView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.e("Sample", "Click Rect !");
            }
        });
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Log.d("Sample", "Ripple completed");
            }
        });
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(Login.this,
                        Registation.class);
                startActivity(i);
                finish();
            }
        });

        // Link to Register Screen
        forgot_password.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(Login.this,
                        ForgotPassword.class);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String email, final String password) {
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

                    // Check for error node in json
                    if (res.equals("true")) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(email,password,true);

                        // Now store the user in SQLite
                        //String datajsobj = jObj.get("data");

                        JSONObject jObj1 = jObj.getJSONObject("detail");

                        String admin_uid = jObj1.getString("userid");
                        String admin_fname = jObj1.getString("first_name");
                        String admin_lname = jObj1.getString("last_name");
                        String admin_email = jObj1.getString("email");
                        String admin_con = jObj1.getString("country");
                        String admin_role = jObj1.getString("roll");

                        String admin_date = jObj1.getString("dtdate");
                        String admin_status = jObj1.getString("status");

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();


                        editor.putString("ADMIN_ID", admin_uid);
                        editor.putString("ROLE", admin_role);
                        editor.putString("NAME", admin_fname+admin_lname);
                        editor.putString("E_MAIL", admin_email);

                        editor.putString("DATE", admin_date);
                        editor.putString("COUNTRY", admin_con);

                        editor.putString("STATUS", admin_status);// Storing string


                        editor.commit();



                     /*   pref.getString("key_name", null); // getting String
                        pref.getInt("key_name", null); // getting Integer
                        pref.getFloat("key_name", null); // getting Float
                        pref.getLong("key_name", null); // getting Long
                        pref.getBoolean("key_name", null);Inserting*/





                       /* editor.remove("name"); // will delete key name
                        editor.remove("email"); // will delete key email

                        editor.commit(); // commit changes


                        editor.clear();
                        editor.commit();

*/
                        // Inserting row in users table
                        // db.addUser(name, email, uid, created_at);

                        // Launch main activity
                        Intent intent = new Intent(Login.this,
                                NewNavigationMain.class);
                        startActivity(intent);
                        finish();
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
                params.put("password", password);

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