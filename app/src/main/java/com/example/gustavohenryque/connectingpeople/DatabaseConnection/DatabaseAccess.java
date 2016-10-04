package com.example.gustavohenryque.connectingpeople.DatabaseConnection;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by gustavohenryque on 03/10/2016.
 */
public class DatabaseAccess {

    private DataabaseOpenHelper openHelper;
    private SQLiteDatabase database;
    private static final String PORTUGUESE_BASE_QUERY = "SELECT portugues FROM palavras WHERE ingles = ?";
    private static final String ENGLISH_BASE_QUERY = "SELECT ingles FROM palavras WHERE portugues = ?";
    private static final String PORTUGUESE_BASE_CONDITION = "portugues = ";
    private static final String ENGLISH_BASE_CONDITION = "ingles = ";

    public DatabaseAccess(Context context){
        this.openHelper = new DataabaseOpenHelper(context, 1);
    }

    public void open(){
        if(this.openHelper != null){
            this.database = openHelper.getWritableDatabase();
        }
    }

    public void close(){
        if(this.database != null && database.isOpen()){
            database.close();
        }
    }

    public List<String> getQuotes() {
        List<String> list = new ArrayList<String>();
        Cursor cursor = database.rawQuery("SELECT portugues FROM palavras", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getTraduction(Map<Integer, List<String>> text) {
        List<String> traductions = new ArrayList<String>();
        List<String> query = new ArrayList<String>();
        String baseQuery = "";

        if (text != null) {
            for (Iterator<Integer> i = text.keySet().iterator(); i.hasNext();) {
                query = text.get(i.next());
                for (Integer unitKey : text.keySet()) {
                    //		for (Integer v = 0; v <= query.size() - 1; v++) {
                    // if(v == 0){
                    // if (unitKey == 0) {
                    // baseQuery = ENGLISH_BASE_QUERY ;
                    // } else {
                    // baseQuery = PORTUGUESE_BASE_QUERY;
                    // }
                    // }else {
                    // if (unitKey == 0) {
                    //baseQuery += " OR portugues = ?";
                    // } else {
                    // baseQuery += " OR ingles = ?";
                    // }
                    // }
                    //	}
                    if (unitKey == 0) {
                        baseQuery = ENGLISH_BASE_QUERY;
                    } else {
                        baseQuery = PORTUGUESE_BASE_QUERY;
                    }

                    for(String value: query){
                        String[] arrayValue = new String[1];
                        arrayValue[0] = value;

                        Cursor cursor = database.rawQuery(baseQuery, arrayValue);
                        cursor.moveToFirst();

                        while (!cursor.isAfterLast()) {
                            traductions.add(cursor.getString(0));
                            cursor.moveToNext();
                        }
                        cursor.close();
                    }
                }
            }
        }
//		String[] arrayQuery = new String[query.size()];
//		arrayQuery = query.toArray(arrayQuery);

//		Cursor cursor = database.rawQuery(baseQuery, arrayQuery);
//		cursor.moveToFirst();

//		while (!cursor.isAfterLast()) {
//			traductions.add(cursor.getString(0));
//			cursor.moveToNext();
//		}

//		cursor.close();

        return traductions;
    }
}
