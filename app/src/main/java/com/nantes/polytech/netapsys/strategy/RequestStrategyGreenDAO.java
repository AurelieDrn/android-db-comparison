package com.nantes.polytech.netapsys.strategy;

import android.content.Context;

import com.nantes.polytech.netapsys.data.MemberData;
import com.nantes.polytech.netapsys.greendao.model.DaoMaster;
import com.nantes.polytech.netapsys.greendao.model.DaoSession;
import com.nantes.polytech.netapsys.greendao.model.Member;
import com.nantes.polytech.netapsys.greendao.model.MemberDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.nantes.polytech.netapsys.AppConstants.EXEC_TIME;
import static com.nantes.polytech.netapsys.AppConstants.listMember;

/**
 * Created by Aurelie on 21/12/2016.
 */

public class RequestStrategyGreenDAO implements RequestStrategy {

    private final String TAG = RequestStrategyGreenDAO.class.getSimpleName().toString();
    private DaoSession daoSession;
    private Database db;

    public RequestStrategyGreenDAO(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "my-db");
        db = helper.getWritableDb();
        this.daoSession = new DaoMaster(db).newSession();
        this.init();
    }

    public void deleteTables() {
        daoSession.getMemberDao().deleteAll();
        daoSession.getInscriptionDao().deleteAll();
        daoSession.getActivityDao().deleteAll();
    }

    @Override
    public void init() {
        MemberDao memberDao = daoSession.getMemberDao();
        //ActivityDao activityDao = daoSession.getActivityDao();
        //InscriptionDao inscriptionDao = daoSession.getInscriptionDao();

        List<Member> myListMember = new ArrayList<>();
        //List<Activity> myListActivity = new ArrayList<>();
        //List<Inscription> myListInscription = new ArrayList<>();

        // creation des objets specifiques a greendao
        for(MemberData mData : listMember) {
            Member m = new Member(mData.getLastName(), mData.getFirstName(), mData.getAge(), mData.getAddress(), mData.getPhoneNumber());
            myListMember.add(m);
        }
        // insertion des objets dans la table
        for(Member m : myListMember) {
            memberDao.insert(m);
        }
        /*
        for(ActivityData mData : listActivity) {
            Activity a = new Activity(mData.getDate(), mData.getPlace(), mData.getName(), mData.getDescription());
            myListActivity.add(a);
        }
        for(Activity a : myListActivity) {
            activityDao.insert(a);
        }

        for(InscriptionData mData : listInscription) {
            Inscription i = new Inscription(myListActivity.get(mData.getIdActivity()-1), myListMember.get(mData.getIdMember()-1));
            myListInscription.add(i);
        }
        for(Inscription i : myListInscription) {
            inscriptionDao.insert(i);
        }*/
    }

    @Override
    public long runSelectRequest() {
        long startTime, endTime;
        List<Long> execTimeList = new ArrayList<Long>();
        QueryBuilder qb = daoSession.getMemberDao().queryBuilder();

        // execute query multiple times
        for(int i = 0; i < EXEC_TIME; i++) {
            //startTime = System.currentTimeMillis();
            startTime = System.nanoTime();
            List result = qb.list();
            //endTime = System.currentTimeMillis();
            endTime = System.nanoTime();
            execTimeList.add(endTime-startTime);
            daoSession.getMemberDao().detachAll();
            //Log.d(TAG, result.toString());
        }

        long sum = 0;
        for(Long t : execTimeList) {
            sum += t;
        }
        return sum/EXEC_TIME;
    }

    @Override
    public long runInsertRequest() {
        long startTime, endTime;
        List<Long> execTimeList = new ArrayList<Long>();
        Member m = new Member("Dupuis", "Alexandrin", 69, "31 Rue Goya", "01.19.98.13.38");
        MemberDao memberDao = daoSession.getMemberDao();

        // execute query multiple times
        for(int i = 0; i < EXEC_TIME; i++) {
            //startTime = System.currentTimeMillis();
            startTime = System.nanoTime();
            memberDao.insert(m);
            //endTime = System.currentTimeMillis();
            endTime = System.nanoTime();
            execTimeList.add(endTime-startTime);


            //QueryBuilder qb = daoSession.getMemberDao().queryBuilder().where(MemberDao.Properties.FirstName.eq("Alexandrin"));
            //List result = qb.list();
            //Log.d(TAG, result.toString());

            memberDao.delete(m);
            daoSession.getMemberDao().detachAll();
        }

        long sum = 0;
        for(Long t : execTimeList) {
            sum += t;
        }

        return sum/EXEC_TIME;
    }

    @Override
    public long runUpdateRequest() {
        long startTime, endTime;
        List<Long> execTimeList = new ArrayList<Long>();
        MemberDao memberDao = daoSession.getMemberDao();

        String updateQuery = "update " + MemberDao.TABLENAME + " set "
                + MemberDao.Properties.FirstName.columnName+ "=?"
                + " where "
                + MemberDao.Properties.FirstName.columnName + "=?";

        // execute query multiple times
        for(int i = 0; i < EXEC_TIME; i++) {
            //startTime = System.currentTimeMillis();
            startTime = System.nanoTime();
            db.execSQL(updateQuery, new Object[] {"Marie", "Clothilde"});
            //endTime = System.currentTimeMillis();
            endTime = System.nanoTime();
            execTimeList.add(endTime-startTime);

            // annuler les modifications
            db.execSQL(updateQuery, new Object[] {"Clothilde", "Marie"});

            // vérifier si on a bien annulé
            QueryBuilder qb = daoSession.getMemberDao().queryBuilder().where(MemberDao.Properties.FirstName.eq("Bruno"));
            List result = qb.list();
            //Log.d(TAG, result.toString());
            daoSession.getMemberDao().detachAll();
        }

        long sum = 0;
        for(Long t : execTimeList) {
            sum += t;
        }

        return sum/EXEC_TIME;
    }

    @Override
    public long runDeleteRequest() {
        long startTime, endTime;
        List<Long> execTimeList = new ArrayList<Long>();
        MemberDao memberDao = daoSession.getMemberDao();
        Member m = new Member("Bourgeau", "Jeanette", 37, "91 rue Gustave Eiffel", "04.95.61.98.76");
        memberDao.insert(m);
        //String deleteQuery = "delete from " + MemberDao.TABLENAME + " where " + MemberDao.Properties.Age.columnName + ">?";
        String deleteQuery = "delete from " + MemberDao.TABLENAME + " where " + MemberDao.Properties.FirstName.columnName + "=?";

        // execute query multiple times
        for(int i = 0; i < EXEC_TIME; i++) {
            //startTime = System.currentTimeMillis();
            startTime = System.nanoTime();
            db.execSQL(deleteQuery, new Object[] {"Jeanette"});
            //endTime = System.currentTimeMillis();
            endTime = System.nanoTime();
            execTimeList.add(endTime-startTime);

            // annuler la suppression
            memberDao.insert(m);

            // verifier que la suppression est annulée
            //QueryBuilder qb = daoSession.getMemberDao().queryBuilder().where(MemberDao.Properties.FirstName.eq("Bruno"));
            //List result = qb.list();
            //Log.d(TAG, result.toString());
            daoSession.getMemberDao().detachAll();
        }

        long sum = 0;
        for(Long t : execTimeList) {
            sum += t;
        }

        return sum/EXEC_TIME;
    }

}
