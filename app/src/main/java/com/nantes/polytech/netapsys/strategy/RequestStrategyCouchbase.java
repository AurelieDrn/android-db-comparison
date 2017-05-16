package com.nantes.polytech.netapsys.strategy;

import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseOptions;
import com.couchbase.lite.Document;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.SavedRevision;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.R.attr.key;
import static com.nantes.polytech.netapsys.AppConstants.EXEC_TIME;
import static com.nantes.polytech.netapsys.AppConstants.listMember;

/**
 * Created by HASSAN29 on 14/03/2017.
 */

public class RequestStrategyCouchbase implements RequestStrategy {
    private final String TAG = RequestStrategyCouchbase.class.getSimpleName().toString();
    private Manager manager;
    private Database mdatabase;
    private Boolean mEncryptionEnabled = false;
    Document documentMember;
    int count=0;
    Map<String, Object> taskMemberListInfo = new HashMap<String, Object>();
    public RequestStrategyCouchbase(AndroidContext context) throws CouchbaseLiteException {
        String dbname = "club_cdb";
        String mUsername="hassan";
        DatabaseOptions options = new DatabaseOptions();
        options.setCreate(true);
        if (mEncryptionEnabled) {
            options.setEncryptionKey(key);
        }
        manager = null;
        try {
            manager = new Manager( context, Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mdatabase = manager.openDatabase(dbname, options);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("type", "task-list");
        properties.put("name", "Member");
        properties.put("owner", mUsername);
        String docId = mUsername + "." + UUID.randomUUID();
        documentMember = mdatabase.getDocument(docId);
        documentMember.putProperties(properties);
        taskMemberListInfo.put("id", documentMember.getId());
        taskMemberListInfo.put("owner", documentMember.getProperty("owner"));
    }
    private SavedRevision createTask(int i,String ln,String fn,int age, String ad, String ph) {

        Map<String, Object> propertiesTask = new HashMap<String, Object>();
        propertiesTask.put("type", "task");
        propertiesTask.put("taskList", taskMemberListInfo);
        propertiesTask.put("createdAt", new Date());
        propertiesTask.put("id", i);
        propertiesTask.put("nom", ln);
        propertiesTask.put("prenom", fn);
        propertiesTask.put("age", age);
        propertiesTask.put("adresse", ad);
        propertiesTask.put("tel", ph);

        Document document = mdatabase.createDocument();
        try {
            return document.putProperties(propertiesTask);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public void init() throws IOException {
        for(int i = 0; i < listMember.size(); i++) {
            createTask(i,listMember.get(i).getLastName(),listMember.get(i).getFirstName(),listMember.get(i).getAge(),listMember.get(i).getAddress(),listMember.get(i).getPhoneNumber());
            count++;
        }
    }

    @Override
    public long runSelectRequest() throws IOException {
        if(mdatabase==null)
        {
            return 0;
        }
        long startTime, endTime;
        List<Long> execTimeList = new ArrayList<Long>();

        com.couchbase.lite.View view = mdatabase.getView("task/tasksByCreatedAt");
        if (view.getMap() == null) {
            view.setMap(new Mapper() {
                @Override
                public void map(Map<String, Object> document, Emitter emitter) {
                    String type = (String) document.get("type");
                    if ("task".equals(type)) {
                        Map<String, Object> taskList = (Map<String, Object>) document.get("taskList");
                        String listId = (String) taskList.get("id");
                        String task = document.get("id")+"";
                        ArrayList<String> key = new ArrayList<String>();
                        key.add(listId);
                        key.add(task);
                        emitter.emit(key, null);
                    }
                }
            }, "5.0");
        }
        Query query = view.createQuery();
        ArrayList<String> key = new ArrayList<String>();
        key.add(documentMember.getId());
        query.setStartKey(key);
        query.setEndKey(key);
        query.setPrefixMatchLevel(1);
        query.setDescending(false);
        try {
            QueryEnumerator qe=query.run();
            for(int tp = 0; tp < EXEC_TIME; tp++)
            {
                startTime = System.nanoTime();
                try {
                    /*for(int i=0; i<query.run().getCount();i++)
                    {
                        Document temp=qe.getRow(i).getDocument();
                    }*/
                    query.run();
                } catch (CouchbaseLiteException e) {
                    e.printStackTrace();
                }
                endTime = System.nanoTime();
                execTimeList.add(endTime - startTime);
            }
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        long sum = 0;
        for(Long t : execTimeList) {
            sum += t;
        }
        return sum/EXEC_TIME;
    }

    @Override
    public long runInsertRequest() throws IOException {
        long startTime, endTime;
        List<Long> execTimeList = new ArrayList<Long>();
        for(int tp = 0; tp < EXEC_TIME; tp++)
        {
            startTime = System.nanoTime();
            SavedRevision task = createTask(count,"Dupuis","Alexandrin", 69, "31 Rue Goya", "01.19.98.13.38");
            endTime = System.nanoTime();
            execTimeList.add(endTime - startTime);
            count++;
            try {
                task.getDocument().delete();
                count--;
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
        }
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
        String nom1="Clothilde", nom2="Marie";
        List<String> l=getListTaskWithFname(nom1);
        for(int tp = 0; tp < EXEC_TIME; tp++)
        {
            for(int i=0;i<l.size();i++)
            {
                startTime = System.nanoTime();
                Map<String, Object> updatedProperties = new HashMap<String, Object>();
                updatedProperties.putAll(mdatabase.getExistingDocument(l.get(i)).getProperties());
                updatedProperties.put("prenom", nom2);
                try {
                    mdatabase.getExistingDocument(l.get(i)).putProperties(updatedProperties);
                } catch (CouchbaseLiteException e) {
                    e.printStackTrace();
                }
                endTime = System.nanoTime();
                execTimeList.add(endTime - startTime);
            }
        }
        for(int i=0;i<l.size();i++)
        {
            Map<String, Object> updatedProperties = new HashMap<String, Object>();
            updatedProperties.putAll(mdatabase.getExistingDocument(l.get(i)).getProperties());
            updatedProperties.put("prenom", nom1);
            try {
                mdatabase.getExistingDocument(l.get(i)).putProperties(updatedProperties);
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
        }
        long sum = 0;
        for(Long t : execTimeList) {
            sum += t;
        }
        return sum/EXEC_TIME;
    }
    private List<String> getListTaskWithFname(String prenom)
    {
        com.couchbase.lite.View view = mdatabase.getView("task/tasksByCreatedAt");
        if (view.getMap() == null) {
            view.setMap(new Mapper() {
                @Override
                public void map(Map<String, Object> document, Emitter emitter) {
                    String type = (String) document.get("type");
                    if ("task".equals(type)) {
                        Map<String, Object> taskList = (Map<String, Object>) document.get("taskList");
                        String listId = (String) taskList.get("id");
                        String task = (String) document.get("prenom");
                        ArrayList<String> key = new ArrayList<String>();
                        key.add(listId);
                        key.add(task);
                        emitter.emit(key, null);
                    }
                }
            }, "5.0");
        }
        Query query = view.createQuery();

        ArrayList<String> key = new ArrayList<String>();
        key.add(documentMember.getId());
        query.setStartKey(key);
        query.setEndKey(key);
        query.setPrefixMatchLevel(1);
        query.setDescending(false);
        List<String> l = new ArrayList<>();
        try {
            for(int i=0; i<query.run().getCount();i++)
            {
                String prenomtemp= (String) query.run().getRow(i).getDocument().getProperty("prenom");

                if(prenomtemp.equals(prenom))
                {
                    l.add(query.run().getRow(i).getDocumentId());
                }
            }
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        return l;
    }

    @Override
    public long runDeleteRequest() throws IOException {
        long startTime, endTime;
        List<Long> execTimeList = new ArrayList<Long>();
        for(int tp = 0; tp < EXEC_TIME; tp++)
        {
            SavedRevision s=createTask(count,"Bourgeau", "Jeanette", 37, "91 rue Gustave Eiffel", "04.95.61.98.76");
            count++;
            try {
                startTime = System.nanoTime();
                s.getDocument().delete();
                endTime = System.nanoTime();
                execTimeList.add(endTime - startTime);
                count--;
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
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
}
