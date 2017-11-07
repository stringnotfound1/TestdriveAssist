package com.esa.beuth.testdriveassist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.berner.mattner.tools.networking.client.Client;
import com.esa.beuth.testdriveassist.gui.CustomConnectVarElement;

import lombok.NonNull;

public class ConnectActivity extends AppCompatActivity {

    private LinearLayout llVarList;
    private TextView tvOk;
    private TextView tvTest;

    private Toast inputToast = null;
    private String inputText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        setTitle(getString(R.string.title_input_connection));

        llVarList = findViewById(R.id.ll_activity_connect);
        tvOk = findViewById(R.id.tv_activity_connect_ok);
        tvTest = findViewById(R.id.tv_activity_connect_test);

        tvOk.setOnClickListener(this::connectClicked);
        tvTest.setOnClickListener(this::testClicked);
    }

    private void connectClicked(final @NonNull View view) {
        Client client = new Client();
        client.setOnInput((length, bytes) -> {
            String input = new String(bytes, 0, length);
            if (!inputText.equals(input)) {
                if (inputToast != null)
                    inputToast.cancel();
                inputToast = Toast.makeText(getApplicationContext(), input, Toast.LENGTH_SHORT);
                inputToast.show();
            }
        });
        new Thread(() -> {
            Looper.prepare();
            try {
                client.start("192.168.43.11", 60000);
                Toast.makeText(getApplicationContext(), "client started", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }).start();

        startActivity(new Intent(this, TestSuiteOverviewActivity.class));
    }

    private void testClicked(final @NonNull View view) {
        CustomConnectVarElement customElement = new CustomConnectVarElement(this);
        customElement.setText("Test");
        llVarList.addView(customElement);
    }
}
