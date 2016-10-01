package com.example.gustavohenryque.connectingpeople.VoiceRecognition;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;

import com.example.gustavohenryque.connectingpeople.Interfaces.TranslationDelegate;

import java.util.ArrayList;

/**
 * Created by gustavohenryque on 27/09/2016.
 */
public class VoiceRecognition implements RecognitionListener {

    TranslationDelegate delegate;

    public VoiceRecognition(TranslationDelegate delegate){
        this.delegate = delegate;
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int i) {
        delegate.recognitionError(i);
    }

    @Override
    public void onResults(Bundle bundle) {
        String str = new String();

        ArrayList data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        for (int i = 0; i < data.size(); i++)
        {
            str += data.get(i);
        }
       delegate.voiceReturn(str);
    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }
}
