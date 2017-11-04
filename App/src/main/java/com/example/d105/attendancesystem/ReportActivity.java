package com.example.d105.attendancesystem;

import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

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

        updateAttendanceValues();
    }

    public void updateAttendanceValues(){

        TextView tvAlgoAtten    = (TextView) findViewById(R.id.tv_sub_algo_attandance);
        TextView tvCompNetAtten = (TextView) findViewById(R.id.tv_sub_comp_network_attandance);
        TextView tvDBMAtten     = (TextView) findViewById(R.id.tv_sub_db_mgmt_attandance);
        TextView tvMathAtten    = (TextView) findViewById(R.id.tv_sub_math_attandance);

        Preference pre          = Preference.getInstance(this);

        Integer algoAtten       = pre.getInt("ALGORITHAM");
        Integer cNetworkAtten   = pre.getInt("COMPNETWORK");
        Integer dbmAtten        = pre.getInt("DBMGMT");
        Integer mathAtten       = pre.getInt("MATH");

        tvAlgoAtten.setText(algoAtten + "%");
        tvCompNetAtten.setText(cNetworkAtten + "%");
        tvDBMAtten.setText(dbmAtten + "%");
        tvMathAtten.setText(mathAtten + "%");
    }
}
