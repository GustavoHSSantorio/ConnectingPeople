package com.example.gustavohenryque.connectingpeople.Fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gustavohenryque.connectingpeople.Interfaces.TranslationDelegate;
import com.example.gustavohenryque.connectingpeople.R;
import com.example.gustavohenryque.connectingpeople.VoiceRecognition.VoiceRecognition;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.TooManyListenersException;

/**
 * Created by gustavohenryque on 26/09/2016.
 */
public class TranslateFragment extends Fragment implements TranslationDelegate{

    private TextView txtPort;
    private TextView txtIng;
    private FloatingActionButton fab;
    private FloatingActionButton vFab;
    private TextToSpeech speech;
    private Snackbar snack;
    private SpeechRecognizer recognizer;

    private Boolean portugueseTranslaction = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.speech = new TextToSpeech(this.getActivity().getApplicationContext(),onInitListener);
        recognizer = SpeechRecognizer.createSpeechRecognizer(getActivity());
        recognizer.setRecognitionListener(new VoiceRecognition(this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View  view = inflater.inflate(R.layout.content_main, container, false);
        return view;
    }

    @Override
    public void onResume() {

        View view = getView();

        if(view != null) {
            this.fab = (FloatingActionButton) view.findViewById(R.id.btn_speak);
            fab.setOnTouchListener(onTouchListener);
            this.vFab = (FloatingActionButton) view.findViewById(R.id.btn_voice);
            vFab.setOnClickListener(onClickListener);

            this.txtIng = (TextView) view.findViewById(R.id.txt_ing);
            this.txtPort = (TextView) view.findViewById(R.id.txt_port);

            this.txtIng.addTextChangedListener(textWatcherIng);
            this.txtPort.addTextChangedListener(textWatcherPort);
        }
        super.onResume();
    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (snack == null)
                snack = (Snackbar) Snackbar.make(view, "Esta Apertando", Snackbar.LENGTH_INDEFINITE);
            switch(motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    snack.show();
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");

                    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
                    recognizer.startListening(intent);
//                    txtIng.setText("pressou");
                    return true; // if you want to handle the touch event
                case MotionEvent.ACTION_UP:
                    if(snack != null)
                        snack.dismiss();
                    recognizer.stopListening();
//                    txtIng.setText("despressou");
                    return true; // if you want to handle the touch event
            }
            return false;
        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            String prhase = "";

            if(portugueseTranslaction){
                if (txtPort.getText() != null)
                    prhase = txtPort.getText().toString();
            }else {
                if (txtIng.getText() != null)
                    prhase = txtIng.getText().toString();
            }
            speak(prhase);
//            speakDeprecated("Estou testando esta API Deprecated");
        }
    };

    private TextWatcher textWatcherIng = new TextWatcher() {

        Integer size = 0;
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            txtPort.removeTextChangedListener(textWatcherPort);
            portugueseTranslaction = true;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String group = editable.toString();
            //txtPort.setText(group);
            txtPort.addTextChangedListener(textWatcherPort);
        }
    };

    private TextWatcher textWatcherPort = new TextWatcher() {
        Integer size = 0;
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            txtIng.removeTextChangedListener(textWatcherIng);
            portugueseTranslaction = false;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            String group = editable.toString();
            //txtIng.setText(group);
            txtIng.addTextChangedListener(textWatcherIng);
        }
    };

    private TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {

            speech.setLanguage(Locale.US);
        }
    };

    @SuppressWarnings("deprecation")
    private void speakDeprecated(String phrase) {
        speech.speak(phrase, TextToSpeech.QUEUE_FLUSH, null);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void speak(String phrase) {

        speech.speak(phrase, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    public void recognitionError(Integer i) {
        snack = (Snackbar) Snackbar.make(getView(), "Deu erro " + i, Snackbar.LENGTH_INDEFINITE);
        snack.show();
    }

    @Override
    public void voiceReturn(String voice) {
        txtIng.setText(voice);
    }
    @Override
    public void translateReturn(List<String> result) {
        if(result != null){
            String phrase = "";
            for(String unitResult: result){
                phrase += " " + unitResult;
            }

            txtIng.setText(phrase);
        }
    }

    @Override
    public void translateError(Exception e) {
        Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
    }

}