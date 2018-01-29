package com.esa.beuth.testdriveassist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends SpeechActivity {

    private static final String TAG = "MainActivity";
    private ImageView ivMenu1;
    private ImageView ivMenu2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        ivMenu1 = findViewById(R.id.iv_activity_main_menu_1);
        ivMenu2 = findViewById(R.id.iv_activity_main_menu_2);

        ivMenu1.setOnClickListener(view -> startActivity(new Intent(this, ConnectionOverviewActivity.class)));
        ivMenu2.setOnClickListener(view -> startActivity(new Intent(this, TestActivity.class)));

        askStoragePermission();
        createXMLDir();


    }

    private void bla(View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void askStoragePermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2909);

        }
    }

    private void createXMLDir() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/" + "TestDriveAssist" + "/files/XML/dummy");
            dir.mkdirs();

            MediaScannerConnection.scanFile(this, new String[]{dir.toString()}, null, null);
            Log.d("MainActivity", "FilePath: " + dir.getAbsolutePath());
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
