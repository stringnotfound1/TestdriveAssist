package com.esa.beuth.testdriveassist.gui;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esa.beuth.testdriveassist.R;

import lombok.Data;

/**
 * Created by Alex on 02.01.2018.
 */
@Data
public class CustomTestSuiteSelect extends LinearLayout {
    TextView tvTestName;
    Button btReset;
    String fileName;

    public CustomTestSuiteSelect(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.custom_test_suite_select, this);
        tvTestName = findViewById(R.id.tv_test_suite_select);
        btReset = findViewById(R.id.bt_test_suite_select_reset);
    }

    public void setText(String text){
        tvTestName.setText(text);
    }

}
