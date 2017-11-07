package com.esa.beuth.testdriveassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esa.beuth.testdriveassist.gui.CustomConnectionOverviewElement;

import java.util.List;

import lombok.NonNull;

public class ConnectionOverviewActivity extends AppCompatActivity {

    private TextView tvOk;
    private TextView tvTest;
    private LinearLayout llConnections;
    private LayoutInflater inflater;
    private List<View> listViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_overview);

        setTitle(getString(R.string.title_select_connection));

        tvOk = findViewById(R.id.tv_activity_connection_overview_ok);
        tvTest = findViewById(R.id.tv_activity_connection_overview_test_list);

        llConnections = findViewById((R.id.ll_activity_connection_overview));

        tvOk.setOnClickListener(this::okClicked);
        tvTest.setOnClickListener(this::testClicked);

        inflater = getLayoutInflater();
    }

    private void okClicked(final @NonNull View view) {
         /* Check for checked Checkboxes and use Intent to send data to next activity */
        Intent intent = new Intent(this, ConnectActivity.class);
        intent.putExtra("EXTRA_ID", "extra");
        startActivity(intent);
    }

    private void testClicked(final @NonNull View view) {
        CustomConnectionOverviewElement customElement = new CustomConnectionOverviewElement(this);
        customElement.setText("This is a test " + customElement.isChecked());
        llConnections.addView(customElement);
    }
}
