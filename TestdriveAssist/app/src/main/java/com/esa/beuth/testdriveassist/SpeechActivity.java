package com.esa.beuth.testdriveassist;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.Locale;

import lombok.NonNull;

/**
 * Created by jschellner on 23.01.2018.
 */

public class SpeechActivity extends AppCompatActivity {

    private static final int SPEECH_REQUEST_CODE = 0;
    private TextToSpeech tts;
    private boolean ttsIsInitialized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tts = new TextToSpeech(this, i -> {
            ttsIsInitialized = i == 0;
            if (ttsIsInitialized) {
                tts.setLanguage(Locale.ENGLISH);
                onTtsCreated();
            }else{
                onTtsCreationFailed();
            }
        });
//        tts.setLanguage(Locale.ENGLISH);
    }

    protected void onTtsCreated() {
    }

    protected void onTtsCreationFailed(){
    }

    protected void speechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            onSpeechInput(results);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onSpeechInput(final @NonNull List<String> speech) {
    }

    protected void textToSpeech(final @NonNull String text) {
        if (ttsIsInitialized)
            tts.speak(text, TextToSpeech.QUEUE_ADD, null, this.hashCode() + "");
    }

    @Override
    protected void onStop() {
        tts.shutdown();
        super.onStop();
    }
}
