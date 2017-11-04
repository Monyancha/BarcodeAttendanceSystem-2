package com.example.d105.attendancesystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registation extends AppCompatActivity {

    private static final String TAG = Registation.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFstName,inputlastname;
    private EditText inputEmail;
    private EditText inputPassword,mobile,addrs,abt,inputmobile;
    //Spinner con_spin;
    private ProgressDialog pDialog;
    private List<CountryModelClass> movieList = new ArrayList<CountryModelClass>();
    private ListView listView;
    // private CustomOrderAdapter adapter;
    CheckBox chk1;
    private SessionManager session;
    // private SQLiteHandler db;
    String token;
    String countryname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);

        inputFstName = (EditText)findViewById(R.id.first_name);
        inputlastname = (EditText) findViewById(R.id.last_name);
        inputEmail = (EditText)findViewById(R.id.email_edit);
        inputPassword = (EditText) findViewById(R.id.password_edit);
        inputmobile = (EditText) findViewById(R.id.mobile);

        btnRegister = (Button) findViewById(R.id.registr_btn);

        // btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());
        // token = SharedPrefManager.getInstance(this).getDeviceToken();
        // SQLite database handler
        // db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not


        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String fname = inputFstName.getText().toString().trim();
                String lname = inputFstName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String mobile_no = inputmobile.getText().toString().trim();


                if (!fname.isEmpty() && !email.isEmpty() && !password.isEmpty() && !lname.isEmpty()
                        && !mobile_no.isEmpty()) {
                    registerUser(fname,lname,email,password,mobile_no);
                }
                else if (!isValidEmail(email.toString())) {
                    inputEmail.setError("Invalid Email");
                }else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }
    private boolean isValidEmail(String email){
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String fname,final String lname,final String email,
                              final String password,final String mobile_no) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    String resvalue = jObj.getString("status");
                    if (resvalue.equals("true")) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("userid");


                       /* JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");
*/
                        // Inserting row in users table
                        //db.addUser(name, email, uid, created_at);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(Registation.this, Login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String msgg = jObj.getString("msg");
                        // Error occurred in registration. Get the error
                        // message
                        // String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                msgg, Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("first_name", fname);
                params.put("last_name", lname);
                params.put("email", email);
                params.put("country", mobile_no);
                params.put("password", password);
                params.put("roll", "Individual");


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
