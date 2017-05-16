package com.nantes.polytech.netapsys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.nantes.polytech.netapsys.R;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        final Button buttonSelect = (Button) findViewById(R.id.btnSelect);
        final Button buttonInsert = (Button) findViewById(R.id.btnInsert);
        final Button buttonUpdate = (Button) findViewById(R.id.btnUpdate);
        final Button buttonDelete = (Button) findViewById(R.id.btnDelete);
        final Intent goToResultActivity = new Intent(RequestActivity.this, ResultActivity.class);
        final ArrayList<String> selectedDatabaseList = getIntent().getStringArrayListExtra("selectedDatabaseList");
        /*final Button btnRetour = (Button) findViewById(R.id.btnRetourRequest);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        // select request
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToResultActivity.putStringArrayListExtra("selectedDatabaseList", selectedDatabaseList);
                goToResultActivity.putExtra("request", "select");
                startActivity(goToResultActivity);
            }
        });

        // insert request
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToResultActivity.putStringArrayListExtra("selectedDatabaseList", selectedDatabaseList);
                goToResultActivity.putExtra("request", "insert");
                startActivity(goToResultActivity);
            }
        });

        // update request
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToResultActivity.putStringArrayListExtra("selectedDatabaseList", selectedDatabaseList);
                goToResultActivity.putExtra("request", "update");
                startActivity(goToResultActivity);
            }
        });

        // delete request
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToResultActivity.putStringArrayListExtra("selectedDatabaseList", selectedDatabaseList);
                goToResultActivity.putExtra("request", "delete");
                startActivity(goToResultActivity);
            }
        });
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        Intent goToDatabaseActivity = new Intent(RequestActivity.this, DatabaseActivity.class);
        startActivity(goToDatabaseActivity);
        //finish();
    }
}
