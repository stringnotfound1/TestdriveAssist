package com.esa.beuth.testdriveassist.gui;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esa.beuth.testdriveassist.R;

/**
 * Created by Alex on 21.10.2017.
 */

public class CustomConnectionOverviewElement extends LinearLayout {

    private CheckBox chkCheckBox;
    private TextView tvConnectionName;

    public CustomConnectionOverviewElement(Context context) {
        super(context);
        init();
    }

    public void setText(String text) {
        tvConnectionName.setText(text);
    }

    public boolean isChecked() {
        return chkCheckBox.isChecked();
    }

    private void init() {
        inflate(getContext(), R.layout.custom_connection_overview_list_element, this);
        chkCheckBox = findViewById(R.id.chk_custom_overview_list_element);
        tvConnectionName = findViewById(R.id.tv_custom_overview_list_element);
    }
}
