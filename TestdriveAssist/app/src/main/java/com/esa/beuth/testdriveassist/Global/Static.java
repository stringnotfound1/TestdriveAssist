package com.esa.beuth.testdriveassist.global;

import android.os.Environment;

import com.berner.mattner.tools.networking.client.Client;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.NonNull;

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

    public static Client client = new Client();

    private static Map<String, String> values = new HashMap<>();
    private static Map<String, List<Consumer<String>>> valueListeners = new HashMap<>();

    public static void setValue(final @NonNull String key, final String value) {
        values.put(key, value);
        if (null != valueListeners.get(key)){
            for (Consumer<String> c : valueListeners.get(key))
                c.accept(value);
        }
    }

    public static Object getValue(final @NonNull String key) {
        return values.get(key);
    }

    public static void registerForValue(final @NonNull String key, final @NonNull Consumer<String> listener) {
        if (!valueListeners.containsKey(key))
            valueListeners.put(key, new LinkedList<>());
        valueListeners.get(key).add(listener);
    }

    public static void unregisterForValue(final @NonNull Consumer<String> listener) {
        String key = null;
        for (Map.Entry<String, List<Consumer<String>>> entry : valueListeners.entrySet()) {
            if (entry.getValue().contains(listener)) {
                key = entry.getKey();
                break;
            }
        }
        if (key != null)
            valueListeners.get(key).remove(listener);
    }
}
