package com.esa.beuth.testdriveassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TestSuiteOverviewActivity extends AppCompatActivity {

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
}
