package com.esa.beuth.testdriveassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esa.beuth.testdriveassist.gui.CustomConnectVarElement;

public class ConnectActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout llVarList;
    private TextView tvOk;
    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        setTitle(getString(R.string.title_input_connection));

        llVarList = (LinearLayout) findViewById(R.id.ll_activity_connect);
        tvOk = (TextView) findViewById(R.id.tv_activity_connect_ok);
        tvTest = (TextView) findViewById(R.id.tv_activity_connect_test);

        tvOk.setOnClickListener(this);
        tvTest.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()){

            case R.id.tv_activity_connect_ok:{

                intent = new Intent(this, TestSuiteOverviewActivity.class);
                startActivity(intent);

                break;
            }
            case R.id.tv_activity_connect_test:{

                CustomConnectVarElement customElement = new CustomConnectVarElement(this);
                customElement.setText("Test");
                llVarList.addView(customElement);

                break;
            }

        }


    }
}
