package com.esa.beuth.testdriveassist.gui;

import android.content.Context;
import android.text.Editable;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esa.beuth.testdriveassist.R;

/**
 * Created by Alex on 21.10.2017.
 */

public class CustomConnectVarElement extends LinearLayout {

    private TextView tvVarName;
    private EditText etVarInput;

    public CustomConnectVarElement(Context context) {
        super(context);
        init();
    }

    public void setText(String text){
        tvVarName.setText(text);
    }

    public String getText(){
        return etVarInput.getText().toString();
    }

    private void init(){
        inflate(getContext(), R.layout.custom_connect_var_element, this);
        tvVarName = (TextView) findViewById(R.id.tv_custom_connect_var_element);
        etVarInput = (EditText) findViewById(R.id.et_custom_connect_var_element);


    }


}
