package com.example.gustavohenryque.connectingpeople.AsyncRequest;

import android.content.Context;
import android.os.AsyncTask;

import com.example.gustavohenryque.connectingpeople.DatabaseConnection.DatabaseAccess;
import com.example.gustavohenryque.connectingpeople.Fragment.TranslateFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gustavohenryque on 03/10/2016.
 */
public class DatabaseAsyncConnection extends AsyncTask {

    TranslateFragment delegate;

    public DatabaseAsyncConnection(TranslateFragment delegate){
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<String> doInBackground(Object[] objects) {

        List<String> result = new ArrayList<>();

        try {
            DatabaseAccess access = new DatabaseAccess(delegate.getActivity());
            access.open();
            result = access.getQuotes();
            access.close();
        }catch (Exception e){
            delegate.translateError(e);
        }

        return result;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        List<String> result = (ArrayList<String>) o;

        delegate.translateReturn(result);

    }
}
