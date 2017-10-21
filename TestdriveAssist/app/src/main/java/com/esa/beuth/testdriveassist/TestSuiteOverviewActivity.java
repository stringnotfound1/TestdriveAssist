package com.esa.beuth.testdriveassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TestSuiteOverviewActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout llTestSuiteList;
    private TextView tvOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_suite_overview);

        setTitle(getString(R.string.title_choose_test));

        llTestSuiteList = (LinearLayout) findViewById(R.id.ll_activity_test_suite_overview);
        tvOk = (TextView) findViewById(R.id.tv_activity_test_suite_overview);

        tvOk.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()){

            case R.id.tv_activity_test_suite_overview:{

                intent = new Intent(this,TestAssistActivity.class);
                startActivity(intent);

                break;
            }

        }

    }
}
