package com.esa.beuth.testdriveassist;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esa.beuth.testdriveassist.Global.Static;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
