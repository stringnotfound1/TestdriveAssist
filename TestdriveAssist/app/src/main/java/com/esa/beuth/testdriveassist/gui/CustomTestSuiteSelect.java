package com.esa.beuth.testdriveassist.gui;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esa.beuth.testdriveassist.R;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Alex on 02.01.2018.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomTestSuiteSelect extends LinearLayout {
    private TextView tvTestName;
    private Button btReset;
    private String fileName;

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
