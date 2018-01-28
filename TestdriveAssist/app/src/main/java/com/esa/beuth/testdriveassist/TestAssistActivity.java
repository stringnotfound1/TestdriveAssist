package com.esa.beuth.testdriveassist;

import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    private ProgressBar pgbProgress;
    private TextView tvProgressLabel;

    private List<TestCase> testCases;
    private Map<TestStep, CustomTestStep> customTestSteps;
    private Consumer<String> listener;
    private CountDownTimer timer;
    private int currentTestCase = 0;
    private int currentTestStep = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_assist);
        setTitle(getString(R.string.title_test_assist));

        ll = findViewById(R.id.ll_activity_test_assist);
        tvSpeed = findViewById(R.id.tv_activity_test_assist_speed);
        tvSteeringAngle = findViewById(R.id.tv_activity_test_steering_angle);
        fabNotes = findViewById(R.id.fab_test_assist_note);
        pgbProgress = findViewById(R.id.pgb_activity_test_progess);
        tvProgressLabel = findViewById(R.id.tv_activity_test_assist_progress_label);

        fabNotes.setOnClickListener(view -> speechToText());
        pgbProgress.setScaleY(10);


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
                    if (testStep.getTime() == null)
                        customTestStep.setText(testStep.getType() + " " + testStep.getComparator().toString().toLowerCase() + " " + testStep.getValue());
                    else
//                        customTestStep.setText(testStep.getType() + " " + testStep.getValue() + " " + getString(R.string.for_test_time) + " " + (testStep.getTime() / 1000) + " s");
                        customTestStep.setText(testStep.getType() + " " + testStep.getComparator().toString().toLowerCase() + " " + testStep.getValue() + " " + " for " + (testStep.getTime() / 1000) + " s");
                    ll.addView(customTestStep);
                    customTestSteps.put(testStep, customTestStep);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onTtsCreated() {
        test(currentTestCase, currentTestStep);
    }

    @Override
    protected void onTtsCreationFailed() {
        test(currentTestCase, currentTestStep);
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
        currentTestCase = testCasesIndex;
        currentTestStep = testStepIndex;

        if (testStep.getTime() == null)
            textToSpeech(testStep.getType() + " " + testStep.getComparator() + " " + testStep.getValue());
        else
            textToSpeech(testStep.getType() + " " + testStep.getComparator() + " " + testStep.getValue() + " " + " for " + (testStep.getTime() / 1000) + " seconds");


        listener = value -> {
            if (timer != null) {
                if (testStep.isConditionMet(value))
                    return;
                timer.cancel();
                timer = null;
                pgbProgress.setProgress(0);
                tvProgressLabel.setText("");
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
                    pgbProgress.setMax(testStep.getTime().intValue());
                    pgbProgress.setProgress(pgbProgress.getMax() - (int) millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    pgbProgress.setProgress(0);
                    tvProgressLabel.setText("");
                    onTestStepSuccessful(testCasesIndex, testStepIndex, testStep);
                }
            };
            String labelText = testStep.getType() + " " + testStep.getComparator().toString().toLowerCase() + " "+ testStep.getValue()+ " " + " for " + (testStep.getTime() / 1000) + " s";
            tvProgressLabel.setText(labelText);
            timer.start();
        };
        Static.registerForValue(testStep.getType(), listener);
    }

    private void onTestStepSuccessful(int testCasesIndex, int testStepIndex, TestStep testStep) {
        customTestSteps.get(testStep).setPassed();
//        textToSpeech(getString(R.string.test_step_success));
        textToSpeech("Test step successful");
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
