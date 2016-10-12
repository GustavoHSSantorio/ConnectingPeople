package com.example.gustavohenryque.connectingpeople.AsyncRequest;

import android.content.Context;
import android.os.AsyncTask;

import com.example.gustavohenryque.connectingpeople.DatabaseConnection.DatabaseAccess;
import com.example.gustavohenryque.connectingpeople.Fragment.TranslateFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gustavohenryque on 03/10/2016.
 */
public class DatabaseAsyncConnection extends AsyncTask {

    TranslateFragment delegate;
    public boolean haserror;

    public DatabaseAsyncConnection(TranslateFragment delegate){
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        Object result;

        try {
            DatabaseAccess access = new DatabaseAccess(delegate.getActivity());
            access.open();
            result = access.getTraduction(delegate.map);
            access.close();
        }catch (Exception e){
            haserror = true;
            result = e;
        }

        return result;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        List<String> result;
        Exception e;

        if(!haserror) {
            result = (ArrayList<String>) o;
            delegate.translateReturn(result);
        }else {
            e = (Exception) o;
            delegate.translateError(e);
        }
    }
}
