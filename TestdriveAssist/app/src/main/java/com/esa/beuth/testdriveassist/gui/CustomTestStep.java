package com.esa.beuth.testdriveassist.gui;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esa.beuth.testdriveassist.R;

/**
 * Created by Alex on 02.01.2018.
 */

public class CustomTestStep extends LinearLayout {

    TextView tvTestStep;
    ImageView ivCheck;
    LinearLayout llTestStep;

    public CustomTestStep(Context context) {
        super(context);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.custom_test_step, this);
        llTestStep = findViewById(R.id.ll_custom_test_step);
        tvTestStep = findViewById(R.id.tv_custom_test_step_name);
        ivCheck = findViewById(R.id.tv_custom_test_step_check);
    }

    public void setText(String text){
        tvTestStep.setText(text);
    }

    public String getText(){
        return tvTestStep.getText().toString();
    }

    public void setPassed(){
        ivCheck.setImageResource(R.drawable.checked);
    }

    public void setFailed(){
        ivCheck.setImageResource(R.drawable.cancel);
    }

    /**
     *
     * @param color Use Resource, e.g. getResources().getColor(R.color.background); or R.color.background
     */
    public void setColor(Integer color){ llTestStep.setBackgroundColor(color); }


}
