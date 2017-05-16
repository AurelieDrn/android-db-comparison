package com.nantes.polytech.netapsys.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.nantes.polytech.netapsys.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DatabaseActivity extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener {

    ListView lv;
    List<MyDatabase> databaseList = new ArrayList<MyDatabase>();
    ArrayAdapter<MyDatabase> cbAdapter;
    List<String> selectedDatabaseList = new ArrayList<String>();
    //HashSet<String> selectedDatabaseList = new HashSet<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        lv = (ListView) findViewById(R.id.listView);
        displayDatabaseList();

        // Button Compare
        final Button button = (Button) findViewById(R.id.btnComparer);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(noDatabaseSelected()) {
                    Toast.makeText(getApplication(), R.string.btn_comparer_erreur, Toast.LENGTH_SHORT).show();
                }
                else {
                    for(int i=0; i<databaseList.size(); i++) {
                        if(databaseList.get(i).isSelected()) {
                            selectedDatabaseList.add(databaseList.get(i).getName());
                        }
                    }
                    Intent goToRequestActivity = new Intent(DatabaseActivity.this, RequestActivity.class);
                    /*List<String> myArrayList = new ArrayList<String>();
                    for(String s : selectedDatabaseList) {
                        myArrayList.add(s);
                    }*/
                    goToRequestActivity.putStringArrayListExtra("selectedDatabaseList", (ArrayList<String>) selectedDatabaseList);
                    //goToRequestActivity.putStringArrayListExtra("selectedDatabaseList", (ArrayList<String>) myArrayList);
                    //startActivity(goToRequestActivity);
                    startActivityForResult(goToRequestActivity, 0);
                }
        }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_CANCELED) {
                // user pressed back from 2nd activity to go to 1st activity. code here
                selectedDatabaseList.clear();
            }
        }
    }

    private void displayDatabaseList() {
        databaseList.add(new MyDatabase("SQLite"));
        databaseList.add(new MyDatabase("GreenDAO"));
        databaseList.add(new MyDatabase("ORMLite"));
        databaseList.add(new MyDatabase("Fichiers"));
        databaseList.add(new MyDatabase("Couchbase"));
        databaseList.add(new MyDatabase("Realm"));

        cbAdapter = new CheckBoxAdapter(databaseList, this);
        lv.setAdapter(cbAdapter);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = lv.getPositionForView(buttonView);
        if(pos != ListView.INVALID_POSITION) {
            MyDatabase myDatabase = databaseList.get(pos);
            myDatabase.setSelected(isChecked);
        }
    }
    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }

    // check if at least one database is selected
    private boolean noDatabaseSelected() {
        for(int i=0; i<databaseList.size(); i++) {
            if(databaseList.get(i).isSelected()) {
                return false;
            }
        }
        return true;
    }

}
