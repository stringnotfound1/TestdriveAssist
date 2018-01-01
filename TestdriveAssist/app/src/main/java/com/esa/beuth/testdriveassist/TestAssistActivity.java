package com.esa.beuth.testdriveassist;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class TestAssistActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private TextView tvReadTTS;
    private TextToSpeech tts;
    private boolean ttsIsInitialized;

    private List currentTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_assist);
        setTitle(getString(R.string.title_test_assist));
        tts = new TextToSpeech(this,this);
//        set Language to phone locale
        tts.setLanguage(Locale.getDefault());
//        tts.setLanguage(Locale.GERMAN);

    }

    private void useTTS(String text){
            if (ttsIsInitialized){
//            tts.speak(etText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, this.hashCode()+"" );
                tts.speak(text, TextToSpeech.QUEUE_ADD, null, this.hashCode()+"" );
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
