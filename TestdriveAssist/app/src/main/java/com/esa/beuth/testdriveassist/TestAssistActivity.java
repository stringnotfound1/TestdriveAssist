package com.esa.beuth.testdriveassist;

import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esa.beuth.testdriveassist.global.Consumer;
import com.esa.beuth.testdriveassist.global.Static;
import com.esa.beuth.testdriveassist.gui.CustomTestStep;
import com.esa.beuth.testdriveassist.xml.TestCase;
import com.esa.beuth.testdriveassist.xml.TestStep;
import com.esa.beuth.testdriveassist.xml.TestSuite;
import com.esa.beuth.testdriveassist.xml.TestXmlParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import lombok.NonNull;

public class TestAssistActivity extends SpeechActivity {

    private static final String TAG = "TestAssist";

    private LinearLayout ll;
    private TextView tvSpeed;
    private TextView tvSteeringAngle;
    private FloatingActionButton fabNotes;

    private List<TestCase> testCases;
    private Map<TestStep, CustomTestStep> customTestSteps;
    private Consumer<String> listener;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_assist);
        setTitle(getString(R.string.title_test_assist));

        ll = findViewById(R.id.ll_activity_test_assist);
        tvSpeed = findViewById(R.id.tv_activity_test_assist_speed);
        tvSteeringAngle = findViewById(R.id.tv_activity_test_steering_angle);
        fabNotes = findViewById(R.id.fab_test_assist_note);

        fabNotes.setOnClickListener(view -> speechToText());

        Log.d(TAG, "FileName: " + getIntent().getStringExtra(Static.TEST_NAME_EXTRA));

        String fileName = getIntent().getStringExtra(Static.TEST_NAME_EXTRA);
        String completePath = "file://" + Static.FILEPATH + Static.XMLPATH + fileName;

        Log.d(TAG, "File Path: " + completePath);

        try {
            TestSuite testSuite = TestXmlParser.parse(completePath);
            customTestSteps = new LinkedHashMap<>();
            testCases = testSuite.getTestCases();
            for (TestCase testCase : testCases) {
                ImageView iv = new ImageView(this);
                iv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.separator));
                ll.addView(iv);
                for (TestStep testStep : testCase.getTestSteps()) {
                    CustomTestStep customTestStep = new CustomTestStep(this);
                    customTestStep.setText(testStep.getType() + " " + testStep.getValue());
                    ll.addView(customTestStep);
                    customTestSteps.put(testStep, customTestStep);
                }
            }
            test(0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void test(int testCasesIndex, int testStepIndex) {
        if (testCasesIndex >= testCases.size())
            return;
        TestCase testCase = testCases.get(testCasesIndex);
        Log.d(TAG, "TestStepIndex: " + testStepIndex + " Size: " + testCase.getTestSteps().size());
        if (testStepIndex >= testCase.getTestSteps().size()) {
            Log.d(TAG, "Next TestCase");
            test(testCasesIndex + 1, 0);
            return;
        }

        TestStep testStep = testCase.getTestSteps().get(testStepIndex);
        listener = value -> {
            if (timer != null) {
                if (testStep.isConditionMet(value))
                    return;
                timer.cancel();
                timer = null;
                // TODO: informiere gui dass teststep fehlgeschlagen ist
                return;
            }
            if (!testStep.isConditionMet(value))
                return;
            if (testStep.getTime() == null) {
                onTestStepSuccessful(testCasesIndex, testStepIndex, testStep);
                return;
            }
            timer = new CountDownTimer(testStep.getTime(), 500) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // gui.setTimeLeft
                }

                @Override
                public void onFinish() {
                    // gui.resetProgressbar
                    onTestStepSuccessful(testCasesIndex, testStepIndex, testStep);
                }
            };
            timer.start();
        };
        Static.registerForValue(testStep.getType(), listener);
    }

    private void onTestStepSuccessful(int testCasesIndex, int testStepIndex, TestStep testStep) {
        customTestSteps.get(testStep).setPassed();
        textToSpeech("TestStep successful");
        Static.unregisterForValue(listener);
        timer = null;
        test(testCasesIndex, testStepIndex + 1);
    }

    private void WriteTextFile(String text) {

        SimpleDateFormat s = new SimpleDateFormat("dd_MM_yyyy_HH:mm:ss", Locale.GERMAN);
        String format = s.format(new Date());
        final File file = new File(Static.FILEPATH + Static.NOTESPATH, "Notes_" + format + ".txt");

        try {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(text);

            myOutWriter.close();

            MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null, null);

            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }

    @Override
    protected void onSpeechInput(final @NonNull List<String> speech) {

        for (String s : speech) {
            Log.d(TAG, "TTS: " + s);
        }
        WriteTextFile(speech.get(0));
    }
}
