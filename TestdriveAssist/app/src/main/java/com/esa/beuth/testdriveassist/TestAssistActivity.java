package com.esa.beuth.testdriveassist;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esa.beuth.testdriveassist.global.Static;
import com.esa.beuth.testdriveassist.gui.CustomTestStep;
import com.esa.beuth.testdriveassist.xml.TestCase;
import com.esa.beuth.testdriveassist.xml.TestCondition;
import com.esa.beuth.testdriveassist.xml.TestStep;
import com.esa.beuth.testdriveassist.xml.TestSuite;
import com.esa.beuth.testdriveassist.xml.TestXmlParser;

import java.util.List;
import java.util.Locale;

public class TestAssistActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static final String TAG = "TestAssist";

    private TextView tvReadTTS;
    private TextToSpeech tts;
    private boolean ttsIsInitialized;
    private Toast inputToast = null;
    private String inputText = "";
    private LinearLayout ll;

    private TextView tvSpeed;
    private TextView tvSteeringAngle;

    private List currentTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_assist);
        setTitle(getString(R.string.title_test_assist));

        ll = findViewById(R.id.ll_activity_test_assist);
        tvSpeed = findViewById(R.id.tv_activity_test_assist_speed);
        tvSteeringAngle = findViewById(R.id.tv_activity_test_steering_angle);

        tts = new TextToSpeech(this, this);
//        set Language to phone locale
//        tts.setLanguage(Locale.getDefault());
        tts.setLanguage(Locale.ENGLISH);

        Log.d(TAG, "FileName: " + getIntent().getStringExtra(Static.TEST_NAME_EXTRA));

        String fileName = getIntent().getStringExtra(Static.TEST_NAME_EXTRA);
        String completePath = "file://" + Static.FILEPATH + Static.XMLPATH + fileName;

        Log.d(TAG, "File Path: " + completePath);

        TestXmlParser testXmlParser = new TestXmlParser();
        try {
            TestSuite testSuite = testXmlParser.parse(completePath);
            List<TestCase> testCases = testSuite.getTestCases();
            for (TestCase testCase : testCases) {
                for (TestStep testStep : testCase.getTestSteps()) {
                    ImageView iv = new ImageView(this);
                    iv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.separator));
                    ll.addView(iv);
                    for (TestCondition testCondition : testStep.getTestConditions()) {
                        CustomTestStep customTestStep = new CustomTestStep(this);
                        customTestStep.setText(testCondition.getType() + " " + testCondition.getValue());
                        ll.addView(customTestStep);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Static.registerForValue(Static.IDENTIFIER_SPEED, value -> {
            tvSpeed.setText(value + "");
            useTTS(getString(R.string.speed) + value);
        });
        Static.registerForValue(Static.IDENTIFIER_STEERING_ANGLE, value -> {
            tvSteeringAngle.setText(value + "");
            useTTS(getString(R.string.steering_angle) + value);
        });
    }

    private void useTTS(String text) {
        if (ttsIsInitialized) {
//            tts.speak(etText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, this.hashCode()+"" );
            tts.speak(text, TextToSpeech.QUEUE_ADD, null, this.hashCode() + "");
        }

    }

    @Override
    public void onInit(int i) {
        if (i == 0)
            ttsIsInitialized = true;
    }

    @Override
    protected void onStop() {
        tts.shutdown();
        super.onStop();
    }
}
