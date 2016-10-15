package com.example.gustavohenryque.connectingpeople.AsyncRequest;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.gustavohenryque.connectingpeople.Interfaces.TranslationDelegate;
import com.google.api.translate.Language;
import com.google.api.translate.Translator;

/**
 * Created by gustavohenryque on 13/10/2016.
 */

public class GoogleTranslatorAsyncAPIRequest extends AsyncTask{

    private TranslationDelegate delegate;
    private boolean hasError;

    public GoogleTranslatorAsyncAPIRequest(TranslationDelegate delegate){
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Object translation = "";
        try {
            Translator.setHttpReferrer("http://code.google.com/apis/language/translate/");
            translation = Translator.execute("test",
                    Language.ENGLISH, Language.FRENCH);
        }catch (Exception e){
            this.hasError = true;
            translation = e;
        }

        return translation;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(!hasError) {
            this.delegate.googleRequestReturn(o.toString());
        }else {
            Exception e = (Exception) o;
            this.delegate.googleRequestError(e);
        }
    }
}
