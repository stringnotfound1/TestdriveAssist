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

import com.berner.mattner.tools.networking.client.Client;
import com.esa.beuth.testdriveassist.global.Static;
import com.esa.beuth.testdriveassist.gui.CustomTestStep;
import com.esa.beuth.testdriveassist.xml.TestCase;
import com.esa.beuth.testdriveassist.xml.TestCondition;
import com.esa.beuth.testdriveassist.xml.TestStep;
import com.esa.beuth.testdriveassist.xml.TestSuite;
import com.esa.beuth.testdriveassist.xml.TestXmlParser;

import java.util.List;
import java.util.Locale;

public class TestAssistActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private static final String TAG ="TestAssist";

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

        tts = new TextToSpeech(this,this);
//        set Language to phone locale
//        tts.setLanguage(Locale.getDefault());
        tts.setLanguage(Locale.ENGLISH);

        Log.d(TAG,"FileName: "+getIntent().getStringExtra(Static.TEST_NAME_EXTRA));

        String fileName = getIntent().getStringExtra(Static.TEST_NAME_EXTRA);
        String completePath = "file://"+Static.FILEPATH+Static.XMLPATH+fileName;

        Log.d(TAG, "File Path: " + completePath);

        TestXmlParser testXmlParser = new TestXmlParser();
        try {
            TestSuite testSuite = testXmlParser.parse(completePath);
            List<TestCase> testCases = testSuite.getTestCases();
            for (TestCase testCase : testCases){
                for (TestStep testStep : testCase.getTestSteps()){
                    ImageView iv = new ImageView(this);
                    iv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.separator));
                    ll.addView(iv);
                    for (TestCondition testCondition : testStep.getTestConditions()){
                        CustomTestStep customTestStep = new CustomTestStep(this);
                        customTestStep.setText(testCondition.getType()+" "+testCondition.getValue());
                        ll.addView(customTestStep);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Static.client.setOnInput((length, bytes) -> {

            // Data has to be sent via intent
            String input = new String(bytes, 0, length);
            Intent intent = new Intent(this, this.getClass());
            intent.setAction(Static.INTENT_DATA_ACTION);
            intent.putExtra(Static.DATA_EXTRA, input);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

    }

    private void useTTS(String text){
            if (ttsIsInitialized){
//            tts.speak(etText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, this.hashCode()+"" );
                tts.speak(text, TextToSpeech.QUEUE_ADD, null, this.hashCode()+"" );
            }

    }

    private void handleIntent(Intent intent) {

     Log.d(TAG,intent.getAction());
     if (Static.INTENT_DATA_ACTION.equals(intent.getAction())){
         String input = intent.getStringExtra(Static.DATA_EXTRA);
         Log.d(TAG, input);
         switch (input.split(":")[0]){
                case Static.IDENTIFIER_SPEED:
                    tvSpeed.setText(input.split(":")[1]);
                    useTTS(getString(R.string.speed)+input.split(":")[1]);
                    break;
                case Static.IDENTIFIER_STEERING_ANGLE:
                    tvSteeringAngle.setText(input.split(":")[1]);
                    useTTS(getString(R.string.steering_angle)+input.split(":")[1]);
                    break;
                default: Log.d(TAG,"Data identifier: " + input.split(":")[0]);
                    break;
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
		/* no super call */
        handleIntent(intent);
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
