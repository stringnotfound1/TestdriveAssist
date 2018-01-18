package com.esa.beuth.testdriveassist.global;

import android.os.Environment;

import com.berner.mattner.tools.networking.client.Client;

/**
 * Created by Alex on 01.01.2018.
 */

public class Static {

    public static final String FILEPATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String XMLPATH = "/TestDriveAssist/files/XML/";

    public static final String TEST_NAME_EXTRA = "TEST_NAME_EXTRA";
    public static final String DATA_EXTRA = "DATA";

    public static final String INTENT_DATA_ACTION = "DATA_ACTION";

    public static final String IDENTIFIER_SPEED = "speed";
    public static final String IDENTIFIER_STEERING_ANGLE = "steeringAngle";

    public static  Client client = new Client();
}
