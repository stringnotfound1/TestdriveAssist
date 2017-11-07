package com.esa.beuth.testdriveassist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TestAssistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_assist);
        setTitle(getString(R.string.title_test_assist));
    }
}
