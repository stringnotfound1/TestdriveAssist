package com.esa.beuth.testdriveassist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.berner.mattner.tools.networking.client.Client;
import com.esa.beuth.testdriveassist.global.Static;
import com.esa.beuth.testdriveassist.gui.CustomConnectVarElement;

import lombok.NonNull;

public class ConnectActivity extends AppCompatActivity {

    private static final String TAG = "ConnectActivity";

    private LinearLayout llVarList;
    private TextView tvOk;
    private TextView tvTest;

    private TextView tvIP;
    private TextView tvPort;


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

        tvIP = findViewById(R.id.tv_activity_connect_IP);
        tvPort = findViewById(R.id.tv_activity_connect_port);

        tvOk.setOnClickListener(this::connectClicked);
        tvTest.setOnClickListener(this::testClicked);

        Toast.makeText(getApplicationContext(), "started", Toast.LENGTH_SHORT).show();

    }

    private void connectClicked(final @NonNull View view) {
        Client client = new Client();
        Log.d(TAG,"Try connection");
        Static.client.setOnInput((length, bytes) -> {

            String input = new String(bytes, 0, length);
            if (!inputText.equals(input)) {
                if (inputToast != null)
                    inputToast.cancel();
                Log.d(TAG,"Data: "+input);
                inputToast = Toast.makeText(this, input, Toast.LENGTH_SHORT);
                inputToast.show();
            }
        });

        new Thread(() -> {
            Looper.prepare();
            try {
                Log.d(TAG,"Try connection");
//                client.start("192.168.178.47", 60000);
                Static.client.start(tvIP.getText().toString(), Integer.parseInt(tvPort.getText().toString()));
                Toast.makeText(getApplicationContext(), "client started", Toast.LENGTH_SHORT).show();
                Log.d(TAG,"Connection success?");
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                Log.e(TAG, "Connection Error");
            }
        }).start();

//        Static.client = client;

        startActivity(new Intent(this, TestSuiteOverviewActivity.class));
    }

    private void testClicked(final @NonNull View view) {
        CustomConnectVarElement customElement = new CustomConnectVarElement(this);
        customElement.setText("Test");
        llVarList.addView(customElement);
    }
}
