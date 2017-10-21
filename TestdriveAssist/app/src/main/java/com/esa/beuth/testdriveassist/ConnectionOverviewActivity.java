package com.esa.beuth.testdriveassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.esa.beuth.testdriveassist.gui.CustomConnectionOverviewElement;

import java.util.List;

public class ConnectionOverviewActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvOk;
    private TextView tvTest;
    private LinearLayout llConnections;
    private LayoutInflater inflater;
    private List<View> listViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_overview);

        tvOk = (TextView) findViewById(R.id.tv_activity_connection_overview_ok);
        tvTest = (TextView) findViewById(R.id.tv_activity_connection_overview_test_list);

        llConnections = (LinearLayout) findViewById((R.id.ll_activity_connection_overview));

        tvOk.setOnClickListener(this);
        tvTest.setOnClickListener(this);

        inflater = getLayoutInflater();

    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch(v.getId()){

            case R.id.tv_activity_connection_overview_ok:{

                break;
            }

            case R.id.tv_activity_connection_overview_test_list:{

                /* Test for ScrollView */
//                View customElement = inflater.inflate(R.layout.custom_connection_overview_list_element, null);
//                TextView tvConnectionName = (TextView) customElement.findViewById(R.id.tv_custom_overview_list_element);
//                tvConnectionName.setText("Placeholder");

                CustomConnectionOverviewElement customElement = new CustomConnectionOverviewElement(this);
                customElement.setText("This is a test "+customElement.isChecked());
                llConnections.addView(customElement);
                
            }
        }

    }
}
