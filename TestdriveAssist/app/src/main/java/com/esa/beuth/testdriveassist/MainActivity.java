package com.esa.beuth.testdriveassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.esa.beuth.testdriveassist.client.Client;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivMenu1;
    private ImageView ivMenu2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivMenu1 = (ImageView) findViewById(R.id.iv_activity_main_menu_1);
        ivMenu2 = (ImageView) findViewById(R.id.iv_activity_main_menu_2);

        ivMenu1.setOnClickListener(this);
        ivMenu2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent;


        switch (v.getId()) {
            case R.id.iv_activity_main_menu_1: {
                intent = new Intent(this, ConnectionOverviewActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.iv_activity_main_menu_2: {
                break;
            }
        }

    }
}
