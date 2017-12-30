package com.esa.beuth.testdriveassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView ivMenu1;
    private ImageView ivMenu2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivMenu1 = findViewById(R.id.iv_activity_main_menu_1);
        ivMenu2 = findViewById(R.id.iv_activity_main_menu_2);

        ivMenu1.setOnClickListener(view -> startActivity(new Intent(this, ConnectionOverviewActivity.class)));
//        ivMenu2.setOnClickListener(this::bla);
        ivMenu2.setOnClickListener(view -> startActivity(new Intent(this,TestActivity.class)));
    }

    private void bla(View view){

    }
}
