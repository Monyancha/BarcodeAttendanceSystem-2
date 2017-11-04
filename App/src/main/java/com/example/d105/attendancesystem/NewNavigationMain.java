package com.example.d105.attendancesystem;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by D105 on 4/7/2017.
 */

public class NewNavigationMain extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    SessionManager session;
    private static final String TAG = NewNavigationMain.class.getSimpleName();
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private TextView txtName, txtWebsite;
    private View navHeader;
    Toolbar toolbar;
    LinearLayout profilein;
    private ImageView imgNavHeaderBg, imgProfile;
    private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "http://www.apertureed.com/wp-content/uploads/2016/08/fig_home-ecosystem-person.png";
    String token;
    private Handler mHandler;
    public static int navItemIndex = 0;
    SharedPreferences pref1;
    SharedPreferences preffb;
    SharedPreferences.Editor fbedit;
    SharedPreferences.Editor gogedit;
    SharedPreferences gogpref;
    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PHOTOS = "photos";
    private static final String TAG_MOVIES = "movies";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;
    String a_name;
    String a_mail;
    CircularTextView totcartitem;
    String fbname,fbmail;
    String a_id;
    // DatabaseHandler db;
    String fb_id,fb_name,fb_mail;
    String g_id,g_name,g_mail;
    private BroadcastReceiver mRegistrationBroadcastReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newnavigation);

        /**
         *Setup the DrawerLayout and NavigationView
         */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        mNavigationView.setItemIconTintList(null);

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */


        // token = SharedPrefManager.getInstance(this).getDeviceToken();
        //Toast.makeText(getBaseContext(),token,Toast.LENGTH_LONG).show();


        navHeader = mNavigationView.getHeaderView(0);

        //  db = new DatabaseHandler(this);

        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(NewNavigationMain.this,UserProfile.class);
                startActivity(in);

                /*Intent in=new Intent(NewNavigationMain.this,ManageActivity.class);
                startActivity(in);*/
            }
        });



        totcartitem = (CircularTextView) findViewById(R.id.circularTextView);
        totcartitem.setStrokeWidth(1);
        totcartitem.setStrokeColor("#ffffff");
        totcartitem.setSolidColor("#ff0000");

        /*int cartval=db.getContactsCount();

        if(cartval!=0) {
            totcartitem.setText(String.valueOf(cartval));
        }
        else
        {
            totcartitem.setVisibility(View.GONE);
        }*/

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new Home_class()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        preffb = getApplicationContext().getSharedPreferences("FacebookPref", 0);
        gogpref = getApplicationContext().getSharedPreferences("GooglePref", 0);
        pref1 = getApplicationContext().getSharedPreferences("Admin", 0);// 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        fbedit=preffb.edit();
        gogedit=gogpref.edit();

        a_id= pref.getString("ADMIN_ID", null); // getting String
        String a_role=pref.getString("ROLE", null); // getting String
        a_name=pref.getString("NAME", null); // getting String
        a_mail=pref.getString("E_MAIL", null); // getting String
        String a_mob=pref.getString("MOBILE", null); // getting String
        String a_add=pref.getString("ADDRESS", null); // getting String
        String a_abt=pref.getString("ABOUT", null); // getting String
        String a_dat=pref.getString("CREATED", null); // getting String
        String a_stats= pref.getString("STATUS", null); // getting String


        fb_id= preffb.getString("FB_ID", null); // getting String

        fb_name=preffb.getString("NAME", null); // getting String
        fb_mail=preffb.getString("E_MAIL", null); // getting String

        g_id= gogpref.getString("G_ID", null); // getting String

        g_name=gogpref.getString("NAME", null); // getting String
        g_mail=gogpref.getString("E_MAIL", null); // getting String

       /* Intent in = getIntent();
       fbname= in.getStringExtra("name");
        fbmail= in.getStringExtra("email");*/

        Log.d("headername",txtName.getText().toString());

        loadNavHeader();
        imgNavHeaderBg.setBackgroundResource(R.drawable.navdef);
        session = new SessionManager(getApplicationContext());
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.profile:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                    case R.id.report:
                        Intent in=new Intent(NewNavigationMain.this,ReportActivity.class);
                        startActivity(in);
                        break;
                    case R.id.time_table:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        Intent in1=new Intent(NewNavigationMain.this,TimeTableA.class);
                        startActivity(in1);
                        break;

                    case R.id.logout:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        logoutUser();
                }

                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);
        /*Drawable drawable = ResourcesCompat.getDrawable(getResources(), android.R.drawable.btn_star, getTheme());
        mDrawerToggle.setHomeAsUpIndicator(drawable);*/
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();




    }
    private void logoutUser() {
        session.setLogin(pref1.getString("email",null),pref1.getString("password",null),false);

        // db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(NewNavigationMain.this, Login.class);
        startActivity(intent);
        finish();
    }
    private void loadNavHeader() {
        // name, website
//Log.d("navname",fbnme);

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        // showing dot next to notifications label
        //navigationView.getMenu().getItem(3).setActionView(R.layout.);
    }




    @Override
    public void onResume(){
        super.onResume();




    }

    /*@Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }
*/
    private void proceedAfterPermission() {
        //We've got the permission, now we can proceed further
        //Toast.makeText(getBaseContext(), "We got the Storage Permission", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
                proceedAfterPermission();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(NewNavigationMain.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewNavigationMain.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();


                            ActivityCompat.requestPermissions(NewNavigationMain.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);


                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(),"Unable to get Permission",Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(NewNavigationMain.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(NewNavigationMain.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")

                .setCancelable(false)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NewNavigationMain.this.finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

}
