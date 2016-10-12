package com.example.gustavohenryque.connectingpeople.DatabaseConnection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by gustavohenryque on 03/10/2016.
 */
public class DataabaseOpenHelper extends SQLiteAssetHelper {

    private static final String DB_NAME = "BDPalavras.db";

    public DataabaseOpenHelper(Context context, Integer version){
        super(context, DB_NAME, null, version);
        setForcedUpgrade();
    }
}
