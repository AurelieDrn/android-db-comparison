package com.nantes.polytech.netapsys.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nantes.polytech.netapsys.sqlite.model.Activity;
import com.nantes.polytech.netapsys.sqlite.model.Inscription;
import com.nantes.polytech.netapsys.sqlite.model.Member;
import com.nantes.polytech.netapsys.sqlite.model.MemberActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HASSAN29 on 17/12/2016.
 */

public class RequestSQLite {
    private final String TAG = RequestSQLite.class.getSimpleName().toString();

    public static String createMemberTable()
    {
        return "CREATE TABLE "
                + Member.MEMBER_TABLE + "("
                + Member.ID_COLUMN + " INTEGER PRIMARY KEY, "
                + Member.NAME_COLUMN + " TEXT, "
                + Member.MEMBER_FNAME + " TEXT, "
                + Member.MEMBER_AGE + " INTEGER, "
                + Member.MEMBER_ADDRESS + " TEXT, "
                + Member.MEMBER_TEL + " TEXT )";
    }
    public static String createActivityTable()
    {
        return "CREATE TABLE "
                + Activity.ACTIVITY_TABLE + "("
                + Activity.ID_COLUMN +" INTEGER PRIMARY KEY, "
                + Activity.NAME_COLUMN + " TEXT, "
                + Activity.ACTIVITY_PLACE + " TEXT, "
                + Activity.ACTIVITY_DESCRIPTION + " TEXT, "
                + Activity.ACTIVITY_DATE + " TEXT )";
    }
    public static String createInscriptionTable()
    {
        return "CREATE TABLE "
                + Inscription.INSCRIPTION_TABLE + "("
                + Inscription.INSCRIPTION_IDMEMBER + " INTEGER, "
                + Inscription.INSCRIPTION_IDACTIVITY + " INTEGER"
                +")";
    }
    //**********************************************************************************************
    public void deleteMemberTab( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Member.MEMBER_TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }
    public void deleteActivityTab( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Activity.ACTIVITY_TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }
    public void deleteInscriptionTab( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Inscription.INSCRIPTION_TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }
    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Member.MEMBER_TABLE,null,null);
        db.delete(Activity.ACTIVITY_TABLE,null,null);
        db.delete(Inscription.INSCRIPTION_TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }
    //**********************************************************************************************
    public long insertMember(Member member)
    {
        long memberId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Member.ID_COLUMN,member.getId());
        values.put(Member.NAME_COLUMN, member.getLastname());
        values.put(Member.MEMBER_FNAME,member.getFirstname());
        values.put(Member.MEMBER_AGE, member.getAge());
        values.put(Member.MEMBER_AGE, member.getAge());
        values.put(Member.MEMBER_ADDRESS,member.getAddress());
        values.put(Member.MEMBER_TEL, member.getTel());
        memberId=db.insert(Member.MEMBER_TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return  memberId;
    }
    //**********************************************************************************************
    public List<Member> getMember(){
        Member member=new Member();
        List<Member> memberList = new ArrayList<Member>();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery =  " SELECT *" +" FROM " + Member.MEMBER_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                member=new Member();
                member.setId(cursor.getInt(cursor.getColumnIndex(Member.ID_COLUMN)));
                member.setLastname(cursor.getString(cursor.getColumnIndex(Member.NAME_COLUMN)));
                member.setFirstname(cursor.getString(cursor.getColumnIndex(Member.MEMBER_FNAME)));
                member.setAge(cursor.getInt(cursor.getColumnIndex(Member.MEMBER_AGE)));
                member.setAddress(cursor.getString(cursor.getColumnIndex(Member.MEMBER_ADDRESS)));
                member.setTel(cursor.getString(cursor.getColumnIndex(Member.MEMBER_TEL)));
                memberList.add(member);
            } while (cursor.moveToNext());
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return memberList;
    }
    //**********************************************************************************************
    public void deleteMember(int id)
    {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery =  "DELETE FROM " + Member.MEMBER_TABLE+" WHERE "+Member.ID_COLUMN+"="+id;

        try{
            db.beginTransaction();
            db.execSQL(selectQuery);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }finally {
            db.endTransaction();
        }

        DatabaseManager.getInstance().closeDatabase();
    }
}
