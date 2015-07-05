package com.kid.words.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by guotao on 15/6/29.
 */
public class DBOpenHelper_Words extends SQLiteOpenHelper {
    public DBOpenHelper_Words(Context context) {
        super(context, "words.db", null, 1);
    }

    //数据库第一次创建时候调用，
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table words(id INTEGER PRIMARY KEY AUTOINCREMENT, word VARCHAR, pronunciation VARCHAR, example VARCHAR)");
    }

    //数据库文件版本号发生变化时调用
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
}
