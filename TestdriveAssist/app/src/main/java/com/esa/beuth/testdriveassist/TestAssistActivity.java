package com.esa.beuth.testdriveassist;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.berner.mattner.tools.networking.client.Client;
import com.esa.beuth.testdriveassist.global.Static;

import java.util.List;
import java.util.Locale;

public class TestAssistActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private static final String TAG ="TestAssist";

    private TextView tvReadTTS;
    private TextToSpeech tts;
    private boolean ttsIsInitialized;

    private Toast inputToast = null;
    private String inputText = "";

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

        Static.client.setOnInput((length, bytes) -> {

            String input = new String(bytes, 0, length);
            if (!inputText.equals(input)) {
                if (inputToast != null)
                    inputToast.cancel();
                Log.d(TAG,"Data: "+input);
                inputToast = Toast.makeText(this, input, Toast.LENGTH_SHORT);
                inputToast.show();
            }
        });

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
