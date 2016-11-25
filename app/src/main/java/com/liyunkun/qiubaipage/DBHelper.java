package com.liyunkun.qiubaipage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liyunkun on 2016/9/7 0007.
 */
public class DBHelper extends SQLiteOpenHelper{
    private static final String DBNAME="users.db";
    private static final int DBVERSION=1;
    public static final String TABLENAME="user";
    public DBHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLENAME+" (_ID INTEGER PRIMARY KEY,USERID,USERIMG,USERNAME,CONTENT,CONTENTIMG,TYPE,UP,DOWN,COMMENTS_COUNT,SHARE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
