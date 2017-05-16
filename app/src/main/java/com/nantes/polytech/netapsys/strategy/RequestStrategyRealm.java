package com.nantes.polytech.netapsys.strategy;

import android.content.Context;
import android.util.Log;

import com.nantes.polytech.netapsys.data.MemberData;
import com.nantes.polytech.netapsys.data.MemberFactory;
import com.nantes.polytech.netapsys.realm.model.Member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static com.nantes.polytech.netapsys.AppConstants.EXEC_TIME;
import static com.nantes.polytech.netapsys.AppConstants.NB_NUPLETS;
import static com.nantes.polytech.netapsys.AppConstants.listMember;

/**
 * Created by Aurelie on 07/04/2017.
 */

public class RequestStrategyRealm implements RequestStrategy {

    private Realm realm;
    private final String TAG = RequestStrategyRealm.class.getSimpleName().toString();

    public RequestStrategyRealm(Context context) {
        realm.init(context);
        realm = Realm.getDefaultInstance();
        this.init();
    }

    @Override
    public void init() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

        List<Member> mListMember = new ArrayList<Member>();

        for(MemberData mData : listMember) {
            Member m = new Member(mData.getLastName(), mData.getFirstName(), mData.getAge(), mData.getAddress(), mData.getPhoneNumber());
            mListMember.add(m);
        }

        // initialisation des données
        realm.beginTransaction();
        for(Member m : mListMember) {
            realm.copyToRealm(m);
        }
        realm.commitTransaction();
    }

    @Override
    public long runSelectRequest() throws IOException {
        long startTime, endTime;
        List<Long> execTimeList = new ArrayList<Long>();

        RealmQuery<Member> query = realm.where(Member.class);

        for (int i = 0; i < EXEC_TIME; i++) {
            startTime = System.nanoTime();
            RealmResults<Member> result = query.findAll();
            endTime = System.nanoTime();
            execTimeList.add(endTime - startTime);
            //Log.d(TAG, result.toString());
        }
        long sum = 0;
        for (Long t : execTimeList) {
            sum += t;
        }
        /*int total = 0;
        for(Member m : result) {
            total++;
        }
        Log.d(TAG, String.valueOf(total));*/
        return sum / EXEC_TIME;
    }

    @Override
    public long runInsertRequest() throws IOException {
        long startTime, endTime;
        List<Long> execTimeList = new ArrayList<Long>();

        Member m = new Member("Dupuis", "Alexandrin", 69, "31 Rue Goya", "01.19.98.13.38");

        /*
        RealmQuery<Member> query = realm.where(Member.class);
        query.equalTo("lastName", "Dupuis");
        query.equalTo("firstName", "Alexandrin");
        */

        for(int i = 0; i < EXEC_TIME; i++) {
            startTime = System.nanoTime();
            realm.beginTransaction();

            realm.copyToRealm(m);

            realm.commitTransaction();

            endTime = System.nanoTime();
            execTimeList.add(endTime-startTime);

            // annuler les insertions
            realm.beginTransaction();
            RealmResults<Member> results = realm.where(Member.class)
                    .equalTo("lastName", "Dupuis")
                    .equalTo("firstName", "Alexandrin")
                    .equalTo("age", 69)
                    .equalTo("address", "31 Rue Goya")
                    .equalTo("phoneNumber", "01.19.98.13.38").findAll();
            results.deleteAllFromRealm();
            realm.commitTransaction();
        }

        /*
        RealmResults<Member> result = query.findAll();
        Log.d(TAG, result.toString());
        */
        long sum = 0;
        for(Long t : execTimeList) {
            sum += t;
        }
        return sum/EXEC_TIME;
    }

    @Override
    public long runUpdateRequest() throws IOException {
        long startTime, endTime;
        List<Long> execTimeList = new ArrayList<Long>();

        RealmQuery<Member> query = realm.where(Member.class);
        query.equalTo("firstName", "Clothilde");

        for(int i = 0; i < EXEC_TIME; i++) {
            startTime = System.nanoTime();

            realm.beginTransaction();

            RealmResults<Member> result = query.findAll();
            for (Member m : result) {
                m.setFirstName("Marie");
            }

            realm.commitTransaction();
            endTime = System.nanoTime();
            execTimeList.add(endTime-startTime);

            // annuler les modifs
            realm.beginTransaction();
            for (Member m : result) {
                m.setFirstName("Clothilde");
            }
            realm.commitTransaction();
        }

        long sum = 0;
        for(Long t : execTimeList) {
            sum += t;
        }
        return sum/EXEC_TIME;
    }

    @Override
    public long runDeleteRequest() throws IOException {
        long startTime, endTime;
        List<Long> execTimeList = new ArrayList<Long>();

        Member m = new Member("Bourgeau", "Jeanette", 37, "91 rue Gustave Eiffel", "04.95.61.98.76");

        // ajouter Jeanette
        realm.beginTransaction();
        realm.copyToRealm(m);
        realm.commitTransaction();

        // supprimer Jeanette
        for(int i = 0; i < EXEC_TIME; i++) {
            startTime = System.nanoTime();

            realm.beginTransaction();
            RealmResults<Member> results = realm.where(Member.class).equalTo("firstName", "Jeanette").findAll();
            results.deleteAllFromRealm();
            realm.commitTransaction();

            endTime = System.nanoTime();
            execTimeList.add(endTime-startTime);

            // réajouter Jeanette
            realm.beginTransaction();
            realm.copyToRealm(m);
            realm.commitTransaction();
        }

        long sum = 0;
        for(Long t : execTimeList) {
            sum += t;
        }
        return sum/EXEC_TIME;
    }

    @Override
    public void deleteTables() {

    }

    private void clearTables() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }
}
