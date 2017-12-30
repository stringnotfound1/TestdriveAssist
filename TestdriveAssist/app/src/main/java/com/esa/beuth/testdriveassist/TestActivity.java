package com.esa.beuth.testdriveassist;

import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeechService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class TestActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private EditText etText;
    private TextView tvReadTTS;
    private TextToSpeech tts;
    private boolean ttsIsInitialized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        etText = findViewById(R.id.et_activity_test_text);
        tvReadTTS = findViewById(R.id.tv_activity_test_tts);

        tvReadTTS.setOnClickListener(this::testTTS);

        tts = new TextToSpeech(this,this);
//        set Language to phone locale
        tts.setLanguage(Locale.getDefault());
//        tts.setLanguage(Locale.GERMAN);

    }

    private void testTTS(View view){
        if (ttsIsInitialized){
//            tts.speak(etText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, this.hashCode()+"" );
            tts.speak(etText.getText().toString(), TextToSpeech.QUEUE_ADD, null, this.hashCode()+"" );
        }

    }

    @Override
    public void onInit(int i) {
        // 0 = SUCCESS -1 = ERROR
        // https://stackoverflow.com/questions/38976808/android-wait-until-text-to-speech-oninit-is-called
        // TODO ?
        if (i == 0)
            ttsIsInitialized = true;
    }

    @Override
    protected void onStop() {
        tts.shutdown();
        super.onStop();
    }
}
