package com.nantes.polytech.netapsys.strategy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nantes.polytech.netapsys.data.MemberData;
import com.nantes.polytech.netapsys.sqlite.DatabaseManager;
import com.nantes.polytech.netapsys.sqlite.MyDataBaseHelper;
import com.nantes.polytech.netapsys.sqlite.RequestSQLite;
import com.nantes.polytech.netapsys.sqlite.model.Activity;
import com.nantes.polytech.netapsys.sqlite.model.Inscription;
import com.nantes.polytech.netapsys.sqlite.model.Member;

import java.util.ArrayList;
import java.util.List;

import static com.nantes.polytech.netapsys.AppConstants.EXEC_TIME;
import static com.nantes.polytech.netapsys.AppConstants.listMember;

/**
 * Created by HASSAN29 on 25/11/2016.
 */

public class RequestStrategySQLite implements RequestStrategy {
    private final String TAG = RequestStrategySQLite.class.getSimpleName().toString();
    RequestSQLite requestSQLite;
    MyDataBaseHelper dbHelper;

    public RequestStrategySQLite(Context context) {
        super();
        dbHelper = new MyDataBaseHelper(context);
        DatabaseManager.initializeInstance(dbHelper);
        requestSQLite = new RequestSQLite();
        this.init();
    }

    @Override
    public void init() {

        List<Member> myListMember = new ArrayList<>();
        List<Activity> myListActivity = new ArrayList<>();
        List<Inscription> myListInscription = new ArrayList<>();

        for(int i = 0; i < listMember.size(); i++) {
            MemberData mData = listMember.get(i);
            Member m = new Member(i, mData.getLastName(), mData.getFirstName(), mData.getAge(), mData.getAddress(), mData.getPhoneNumber());
            myListMember.add(m);
        }

        for(int i = 0; i < myListMember.size(); i++)
            requestSQLite.insertMember(myListMember.get(i));
    }

    @Override
    public long runSelectRequest() {
        Member member=new Member();
        List<Member> memberList = new ArrayList<Member>();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery =  " SELECT *" +" FROM " + Member.MEMBER_TABLE;
        List<Long> listTime = new ArrayList<Long>();
        memberList.clear();
        long startTime,endTime;
        for(int i = 0; i < EXEC_TIME; i++) {
            startTime = System.nanoTime();
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
            endTime = System.nanoTime();
            listTime.add(endTime-startTime);
            if(i < EXEC_TIME-1)
                memberList.clear();
        }
        DatabaseManager.getInstance().closeDatabase();
        Long averageTime, sumTime = 0L;
        for(int i = 0; i < listTime.size(); i++)
        {
            sumTime += listTime.get(i);
        }
        return sumTime/EXEC_TIME;
    }

    @Override
    public long runInsertRequest() {
        Member m = new Member(120,"Dupuis", "Alexandrin", 69, "31 Rue Goya", "01.19.98.13.38");
        List<Long> listTime=new ArrayList<Long>();
        long startTime,endTime;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        for(int i = 0; i < EXEC_TIME; i++) {
            startTime = System.nanoTime();
            ContentValues values = new ContentValues();
            values.put(Member.ID_COLUMN,m.getId());
            values.put(Member.NAME_COLUMN, m.getLastname());
            values.put(Member.MEMBER_FNAME,m.getFirstname());
            values.put(Member.MEMBER_AGE, m.getAge());
            values.put(Member.MEMBER_AGE, m.getAge());
            values.put(Member.MEMBER_ADDRESS,m.getAddress());
            values.put(Member.MEMBER_TEL, m.getTel());
            db.insert(Member.MEMBER_TABLE, null, values);
            endTime = System.nanoTime();
            listTime.add(endTime-startTime);
            requestSQLite.deleteMember(120);
        }
        DatabaseManager.getInstance().closeDatabase();
        Long sumTime = 0L;
        for(int i = 0; i < listTime.size(); i++) {
            sumTime += listTime.get(i);
        }
        return (sumTime/EXEC_TIME);
    }

    @Override
    public long runUpdateRequest() {
        String nom1="Clothilde", nom2="Marie";
        String selectQuery =  "UPDATE " + Member.MEMBER_TABLE+" SET "+Member.MEMBER_FNAME+"='"+nom2+"' WHERE "+Member.MEMBER_FNAME+"='"+nom1+"'";
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        List<Long> listTime = new ArrayList<Long>();
        long startTime, endTime;
        for(int i = 0; i < EXEC_TIME; i++) {
            selectQuery =  "UPDATE " + Member.MEMBER_TABLE+" SET "+Member.MEMBER_FNAME+"='"+nom2+"' WHERE "+Member.MEMBER_FNAME+"='"+nom1+"'";
            try{
                startTime = System.nanoTime();
                db.beginTransaction();
                db.execSQL(selectQuery);
                db.setTransactionSuccessful();
                db.endTransaction();
                endTime = System.nanoTime();
                listTime.add(endTime-startTime);
                selectQuery =  "UPDATE " + Member.MEMBER_TABLE+" SET "+Member.MEMBER_FNAME+"='"+nom1+"' WHERE "+Member.MEMBER_FNAME+"='"+nom2+"'";
                db.beginTransaction();
                db.execSQL(selectQuery);
                db.setTransactionSuccessful();
                db.endTransaction();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        Long averageTime, sumTime = 0L;
        for(int i = 0; i < listTime.size(); i++) {
            sumTime += listTime.get(i);
        }
        DatabaseManager.getInstance().closeDatabase();
        return (sumTime/EXEC_TIME);
    }

    @Override
    public long runDeleteRequest() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery =  "DELETE FROM " + Member.MEMBER_TABLE+" WHERE "+Member.ID_COLUMN+"="+100;
        Member m = new Member(100,"Bourgeau", "Jeanette", 37, "91 rue Gustave Eiffel", "04.95.61.98.76");
        requestSQLite.insertMember(m);
        List<Long> listTime = new ArrayList<Long>();
        long startTime, endTime;
        for(int i = 0; i < EXEC_TIME; i++) {

            try{
                startTime = System.nanoTime();
                db.beginTransaction();
                db.execSQL(selectQuery);
                db.setTransactionSuccessful();
                endTime = System.nanoTime();
                listTime.add(endTime-startTime);
                requestSQLite.insertMember(m);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }finally {
                db.endTransaction();
            }
        }
        DatabaseManager.getInstance().closeDatabase();
        Long sumTime = 0L;
        for(int i = 0; i < listTime.size(); i++) {
            sumTime += listTime.get(i);
        }
        return (sumTime/EXEC_TIME);
    }

    @Override
    public void deleteTables() {
        requestSQLite.delete();
    }
}
