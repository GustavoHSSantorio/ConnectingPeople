package com.example.gustavohenryque.connectingpeople.DatabaseConnection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gustavohenryque on 03/10/2016.
 */
public class DataabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "BDPalavras";

    public DataabaseOpenHelper(Context context, Integer version){
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
