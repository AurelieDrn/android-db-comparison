package com.nantes.polytech.netapsys.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import com.nantes.polytech.netapsys.R;
import com.nantes.polytech.netapsys.ormlite.model.Inscription;
import com.nantes.polytech.netapsys.ormlite.model.Member;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by HASSAN29 on 19/01/2017.
 */

public class OrmliteDatabaseHelper extends OrmLiteSqliteOpenHelper {
    private final String TAG = OrmliteDatabaseHelper.class.getSimpleName().toString();
    private static final String DATABASE_NAME = "db_club2";
    private static final int DATABASE_VERSION = 1;
    private Dao<Member, Integer> memberDao;
    private Dao<Inscription, Integer> inscriptionDao;
    // we do this so there is only one helper
    private static OrmliteDatabaseHelper helper = null;
    private static final AtomicInteger usageCounter = new AtomicInteger(0);

    public OrmliteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Member.class);
            TableUtils.createTable(connectionSource, Inscription.class);
        } catch (SQLException e) {
            Log.e(OrmliteDatabaseHelper.class.getName(), "Unable to create ormlite database", e);
        }


    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            TableUtils.dropTable(connectionSource, Member.class, true);
            TableUtils.dropTable(connectionSource, Inscription.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            //e.printStackTrace();
            Log.e(OrmliteDatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }

    }

    // Create the getDao methods of all database tables to access those from android code.
    // Insert, delete, read, update everything will be happened through DAOs
    public Dao<Member, Integer> getMemberDao() throws SQLException {
        if (memberDao == null) {
            memberDao = getDao(Member.class);
        }
        return memberDao;
    }
    public Dao<Inscription, Integer> getInscriptionDao() throws SQLException {
        if (inscriptionDao == null) {
            inscriptionDao = getDao(Inscription.class);
        }
        return inscriptionDao;
    }
    //*************************************************************
    public static synchronized OrmliteDatabaseHelper getHelper(Context context) {
        if (helper == null) {
            helper = new OrmliteDatabaseHelper(context);
        }
        usageCounter.incrementAndGet();
        return helper;
    }

    @Override
    public void close() {
        super.close();
        if (usageCounter.decrementAndGet() == 0) {
            super.close();
            memberDao = null;
            inscriptionDao = null;
            helper = null;
        }
    }
}