package com.esa.beuth.testdriveassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esa.beuth.testdriveassist.global.Static;

import java.io.File;

public class TestSuiteOverviewActivity extends AppCompatActivity {


    static final String TAG = "TestSuiteOverview";
    private LinearLayout llTestSuiteList;
    private TextView tvOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_suite_overview);

        setTitle(getString(R.string.title_choose_test));

        llTestSuiteList = findViewById(R.id.ll_activity_test_suite_overview);
        tvOk = findViewById(R.id.tv_activity_test_suite_overview);

        tvOk.setOnClickListener(view -> startActivity(new Intent(this, TestAssistActivity.class)));

        Toast.makeText(getApplicationContext(), "started", Toast.LENGTH_SHORT).show();


    }

    private void listTestSuites(){

        File xmlDir = new File(Static.FILEPATH+Static.XMLPATH);

        for (File f : xmlDir.listFiles()){
            if(f.getPath().endsWith("xml")){
                Log.d(TAG, f.getAbsolutePath());
            }
        }



    }

}
