package com.nantes.polytech.netapsys.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nantes.polytech.netapsys.sqlite.model.Activity;
import com.nantes.polytech.netapsys.sqlite.model.Inscription;
import com.nantes.polytech.netapsys.sqlite.model.Member;

/**
 * Created by HASSAN29 on 15/11/2016.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="db_club1";
    private static final int DATABASE_VERSION=1;
    private static final String TAG = MyDataBaseHelper.class.getSimpleName().toString();

    public MyDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create tables
        db.execSQL(RequestSQLite.createMemberTable());
        db.execSQL(RequestSQLite.createActivityTable());
        db.execSQL(RequestSQLite.createInscriptionTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Member.MEMBER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Activity.ACTIVITY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Inscription.INSCRIPTION_TABLE);
        //create new tables
        onCreate(db);
    }
}