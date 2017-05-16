package com.nantes.polytech.netapsys.strategy;


import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.nantes.polytech.netapsys.data.MemberData;
import com.nantes.polytech.netapsys.ormlite.OrmliteDatabaseHelper;
import com.nantes.polytech.netapsys.ormlite.model.Member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.nantes.polytech.netapsys.AppConstants.EXEC_TIME;
import static com.nantes.polytech.netapsys.AppConstants.listMember;

public class RequestStrategyORMLite implements RequestStrategy {
    private final String TAG = RequestStrategyORMLite.class.getSimpleName().toString();
    private OrmliteDatabaseHelper databaseHelper;
    private Dao<Member,Integer> memberdao;
    private Context context;
    public RequestStrategyORMLite(Context context) {
        this.context = context;

        List<Member> myListMember = new ArrayList<>();

        // creation des objets specifiques a orm lite
        for(MemberData mData : listMember) {
            Member m = new Member(mData.getLastName(), mData.getFirstName(), mData.getAge(), mData.getAddress(), mData.getPhoneNumber());
            myListMember.add(m);
        }

        try {
            memberdao=getHelper().getMemberDao();
            for(Member m : myListMember) {
                memberdao.create(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private OrmliteDatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OrmliteDatabaseHelper.getHelper(context);
        }
        return databaseHelper;
    }
    @Override
    public void init() {
    }

    @Override
    public long runSelectRequest() {
        List<Long> listTime = new ArrayList<Long>();
        long startTime,endTime;
        try {
            memberdao=getHelper().getMemberDao();
            for(int i = 0; i < EXEC_TIME; i++) {
                startTime = System.nanoTime();
                GenericRawResults<String[]> rawResults = memberdao.queryRaw("select * from member_tab;");
                endTime = System.nanoTime();
                listTime.add(endTime-startTime);
            }

            Long averageTime, sumTime = 0L;
            for(int i = 0; i < listTime.size(); i++)
            {
                sumTime += listTime.get(i);
            }
            averageTime = sumTime/EXEC_TIME;
            return averageTime;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public long runInsertRequest() {
        long startTime, endTime;
        List<Long> execTimeList = new ArrayList<Long>();
        Member m = new Member("Dupuis", "Alexandrin", 69, "31 Rue Goya", "01.19.98.13.38");
        try {
            memberdao=getHelper().getMemberDao();
            for(int i = 0; i < EXEC_TIME; i++) {
                startTime = System.nanoTime();
                memberdao.create(m);
                endTime = System.nanoTime();
                execTimeList.add(endTime-startTime);
                memberdao.delete(m);
            }
            long sum = 0;
            for(Long t : execTimeList) {
                sum += t;
            }
            return sum/EXEC_TIME;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public long runUpdateRequest() {
        long startTime, endTime;
        List<Long> execTimeList = new ArrayList<Long>();
        Member m = new Member("Dupuis", "Martin", 69, "31 Rue Goya", "01.19.98.13.38");
        try {
            memberdao=getHelper().getMemberDao();
            for(int i = 0; i < EXEC_TIME; i++) {
                startTime = System.nanoTime();
                memberdao.executeRaw("update member_tab set member_firstname='Marie' where member_firstname='Clothilde'");
                endTime = System.nanoTime();
                execTimeList.add(endTime - startTime);
                memberdao.executeRaw("update member_tab set member_firstname='Clothilde' where member_firstname='Marie'");
            }
            long sum = 0;
            for(Long t : execTimeList) {
                sum += t;
            }
            return sum/EXEC_TIME;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public long runDeleteRequest() {
        long startTime, endTime;
        List<Long> execTimeList = new ArrayList<Long>();
        Member m = new Member("Bourgeau", "Jeanette", 37, "91 rue Gustave Eiffel", "04.95.61.98.76");
        try {
            memberdao=getHelper().getMemberDao();
            memberdao.create(m);
            for(int i = 0; i < EXEC_TIME; i++) {
                startTime = System.nanoTime();
                memberdao.delete(m);
                endTime = System.nanoTime();
                execTimeList.add(endTime-startTime);
                memberdao.create(m);
            }
            long sum = 0;
            for(Long t : execTimeList) {
                sum += t;
            }
            return sum/EXEC_TIME;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void deleteTables() {
        if (databaseHelper != null) {
            databaseHelper.close();
            databaseHelper = null;
            context.deleteDatabase("db_club");
        }
        OpenHelperManager.releaseHelper();
    }
}
