package com.esa.beuth.testdriveassist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esa.beuth.testdriveassist.global.Static;
import com.esa.beuth.testdriveassist.gui.CustomConnectVarElement;

import java.io.File;

import lombok.NonNull;

public class ConnectActivity extends SpeechActivity {

    private static final String TAG = "ConnectActivity";

    private LinearLayout llVarList;
    private TextView tvOk;

    private TextView tvIP;
    private TextView tvPort;

    private Toast inputToast = null;
    private String inputText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        tvOk = findViewById(R.id.tv_activity_connect_ok);
        tvIP = findViewById(R.id.tv_activity_connect_IP);
        tvPort = findViewById(R.id.tv_activity_connect_port);

        tvOk.setOnClickListener(this::connectClicked);

        askStoragePermission();
        createDir(Static.XMLPATH);
        createDir(Static.NOTESPATH);

        Toast.makeText(getApplicationContext(), "started", Toast.LENGTH_SHORT).show();

    }

    private void connectClicked(final @NonNull View view) {
        Log.d(TAG, "Try connection");
        Static.client.setOnInput((length, bytes) -> runOnUiThread(() -> {
            String input = new String(bytes, 0, length);
            Log.d(TAG, "Input Data: " + input);
            String[] split = input.split(":");
            Log.d(TAG, "Split length: " + split.length + " " + split[1]);
            Static.setValue(split[0], split[1]);
        }));

        new Thread(() -> {
            Looper.prepare();
            try {
                Log.d(TAG, "Try connection");
                Static.client.start(tvIP.getText().toString(), Integer.parseInt(tvPort.getText().toString()));
                Toast.makeText(getApplicationContext(), "client started", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Connection Success");
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                Log.e(TAG, "Connection Error");
            }
        }).start();

        startActivity(new Intent(this, TestSuiteOverviewActivity.class));
    }

    private void askStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2909);
    }

    private void createDir(String s) {

        Log.d(TAG, "Create Dir: " + s);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Attempt to create Dir: " + s);
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + s + "/dummy");
            dir.mkdirs();
            MediaScannerConnection.scanFile(this, new String[]{dir.toString()}, null, null);
            new File(sdCard.getAbsolutePath() + s + "/dummy").delete();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d(TAG, "onRequest " + permissions + " " + grantResults);
        switch (requestCode) {
            case 2909: {
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Permission", "Granted");
                    } else {
                        Log.e("Permission", "Denied");
                        Toast.makeText(getApplicationContext(), R.string.permission_storage, Toast.LENGTH_SHORT).show();
                    }
                }
                return;
            }
        }
    }
}
