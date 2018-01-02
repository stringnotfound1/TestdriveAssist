package com.esa.beuth.testdriveassist.global;

import android.os.Environment;

import com.berner.mattner.tools.networking.client.Client;

/**
 * Created by Alex on 01.01.2018.
 */

public class Static {

    public static final String FILEPATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String XMLPATH = "/TestDriveAssist/files/XML/";

    public static  Client client = new Client();

}
