package com.esa.beuth.testdriveassist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esa.beuth.testdriveassist.global.Static;
import com.esa.beuth.testdriveassist.gui.CustomTestSuiteSelect;

import java.io.File;
import java.util.ArrayList;

public class TestSuiteOverviewActivity extends SpeechActivity {


    static final String TAG = "TestSuiteOverview";
    private LinearLayout llTestSuiteList;
    private TextView tvOk;

    private ArrayList<String> xmlFileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_suite_overview);

        setTitle(getString(R.string.title_choose_test));

        llTestSuiteList = findViewById(R.id.ll_activity_test_suite_overview);
        tvOk = findViewById(R.id.tv_activity_test_suite_overview);
        xmlFileList = new ArrayList<>();

        tvOk.setOnClickListener(view -> startActivity(new Intent(this, TestAssistActivity.class)));


//        Toast.makeText(getApplicationContext(), "select Test", Toast.LENGTH_SHORT).show();
        listTestSuites();
//        llTestSuiteList.addView();
        if (xmlFileList.size()>0){
            for (String s : xmlFileList){
                CustomTestSuiteSelect vTestSelect= new CustomTestSuiteSelect(this);
                vTestSelect.setText(s);
                vTestSelect.setFileName(s);
                vTestSelect.setOnClickListener(view -> startActivity(new Intent(this,TestAssistActivity.class).putExtra(Static.TEST_NAME_EXTRA,s)));
                vTestSelect.getBtReset().setOnClickListener(view -> Toast.makeText(this, "RESET "+vTestSelect.getFileName(), Toast.LENGTH_SHORT).show());
//                complete path to file: String completePath = "file://" + Static.FILEPATH + Static.XMLPATH + fileName;
                llTestSuiteList.addView(vTestSelect);
            }

        }

    }

    private void listTestSuites(){

        File xmlDir = new File(Static.FILEPATH+Static.XMLPATH);

         if (xmlDir.listFiles().length > 0) {
             for (File f : xmlDir.listFiles()) {
                 if (f.getPath().endsWith("xml")) {
                     xmlFileList.add(f.getName());
                     Log.d(TAG, f.getAbsolutePath());
                 }
             }
         }



    }

}
