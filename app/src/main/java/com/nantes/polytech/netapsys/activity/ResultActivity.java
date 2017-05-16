package com.nantes.polytech.netapsys.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.android.AndroidContext;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.nantes.polytech.netapsys.R;
import com.nantes.polytech.netapsys.strategy.RequestStrategy;
import com.nantes.polytech.netapsys.strategy.RequestStrategyCouchbase;
import com.nantes.polytech.netapsys.strategy.RequestStrategyFile;
import com.nantes.polytech.netapsys.strategy.RequestStrategyGreenDAO;
import com.nantes.polytech.netapsys.strategy.RequestStrategyORMLite;
import com.nantes.polytech.netapsys.strategy.RequestStrategyRealm;
import com.nantes.polytech.netapsys.strategy.RequestStrategySQLite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private static final String TAG = ResultActivity.class.getSimpleName().toString();

    private boolean desactive=true;
    private RequestStrategy rq;

    private List<RequestStrategy> rqList;

    private Intent goToResultActivity;
    private ProgressBar mProgressBar;

    private TextView textView;
    private TextView loadingText;
    private TextView titre;

    private ArrayList<String> selectedDatabaseList;
    private ArrayList<BarEntry> barEntries;
    private BarDataSet barDataSet;
    private BarData barData;

    private class ResultProgress extends AsyncTask<Object, Object, List<BarEntry>> {
        BarChart barChart = (BarChart) findViewById(R.id.chart1);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            desactive=true;
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
            textView.setVisibility(ProgressBar.INVISIBLE);
            barChart.setVisibility(BarChart.INVISIBLE);
            loadingText = (TextView) findViewById(R.id.loadingText);
            titre = (TextView) findViewById(R.id.titre);

            //Toast.makeText(getApplicationContext(), "Début du traitement asynchrone", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Object... values){
            super.onProgressUpdate(values);
            // Mise à jour de la ProgressBar
            //mProgressBar.setProgress((Integer) values[0]);

            //Toast.makeText(getApplicationContext(), (String) values[0], Toast.LENGTH_LONG).show();
        }

        @Override
        protected List<BarEntry> doInBackground(Object... strings) {
            final String request = getIntent().getStringExtra("request");
            selectedDatabaseList = getIntent().getStringArrayListExtra("selectedDatabaseList");;
            barEntries = new ArrayList<BarEntry>();
            rqList = new ArrayList<>();
            float indice = 0.f;

            if(selectedDatabaseList != null) {
                for(final String databaseName : selectedDatabaseList) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingText.setText("Exécution de requêtes sur "+databaseName);
                            titre.setText("Temps moyens d'exécution de la requête "+request+" en microsecondes");
                        }
                    });

                    long result = 0;
                    switch(databaseName) {
                        case "SQLite":
                            rq = new RequestStrategySQLite(getApplicationContext());
                            break;
                        case "GreenDAO":
                            rq = new RequestStrategyGreenDAO(getApplicationContext());
                            break;
                        case "ORMLite":
                            rq = new RequestStrategyORMLite(getApplicationContext());
                            break;
                        case "Fichiers":
                            try {
                                rq = new RequestStrategyFile(getApplicationContext());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Couchbase":
                            try {
                                rq = new RequestStrategyCouchbase(new AndroidContext(getApplicationContext()));
                                rq.init();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (CouchbaseLiteException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Realm":
                            rq = new RequestStrategyRealm(getApplicationContext());
                            break;
                        default:
                            rq = null;
                    }
                    // execution des requetes
                    if(rq != null) {
                        try {
                            if(request.equals("select")) {
                                result = rq.runSelectRequest();
                            }
                            else if(request.equals("insert")) {
                                result = rq.runInsertRequest();
                            }
                            else if(request.equals("update")) {
                                result = rq.runUpdateRequest();
                            }
                            else {
                                result = rq.runDeleteRequest();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        rqList.add(rq);
                    }
                    long res = result/1000; // resultat en microsecondes
                    barEntries.add(new BarEntry(indice, res));
                    indice++; // incrementer l indice pour passer a la barre suivante du graphique
                }
                for(RequestStrategy r : rqList) {
                    r.deleteTables();
                }
            }
            return barEntries;
        }

        @Override
        protected void onPostExecute(List<BarEntry> result) {
            desactive=false;
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            textView.setVisibility(ProgressBar.VISIBLE);
            barChart.setVisibility(BarChart.VISIBLE);
            loadingText.setVisibility(TextView.INVISIBLE);

            if(selectedDatabaseList != null) {
                List<BarEntry> sortedBarEntries = new ArrayList<>();
                ArrayList<String> myDatabaseList = (ArrayList) selectedDatabaseList.clone();
                float rang = 0.f;
                List<String> sortedDatabaseList = new ArrayList<>();
                while(barEntries.isEmpty() == false) {
                    float min = barEntries.get(0).getY();
                    BarEntry minEntry = barEntries.get(0);
                    int index = 0;
                    for(int i=0; i<barEntries.size(); i++) {
                        if(Float.compare(barEntries.get(i).getY(), min) < 0) {
                            min = barEntries.get(i).getY();
                            minEntry = barEntries.get(i);
                            index = i;
                        }
                    }
                    sortedBarEntries.add(new BarEntry(rang, minEntry.getY()));
                    rang++;
                    sortedDatabaseList.add(myDatabaseList.get(index));
                    myDatabaseList.remove(index);
                    barEntries.remove(index);
                }

                barChart = (BarChart) findViewById(R.id.chart1);
                barDataSet = new BarDataSet(sortedBarEntries, "");
                barDataSet.setColor(Color.rgb(155,25,21));
                barDataSet.setValueTextSize(15);

                barData = new BarData(barDataSet);
                barData.setBarWidth(0.9f);

                barChart.setData(barData);
                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(sortedDatabaseList));
                barChart.getXAxis().setGranularity(1f);
                barChart.setFitBars(true);

                // masquer les barres a gauche et a droite
                barChart.getAxisLeft().setEnabled(false);
                barChart.getAxisRight().setEnabled(false);

                // axe horizontal en bas
                barChart.getXAxis().setDrawGridLines(false);
                barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                barChart.getLegend().setWordWrapEnabled(true);

                // masquer les textes inutiles
                barChart.setDescription(null);
                barChart.getLegend().setEnabled(false);

                // interaction
                barChart.setScaleEnabled(false);

                barChart.invalidate();

                //chart.animateY(3000);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mProgressBar = (ProgressBar) findViewById(R.id.pBAsync);
        mProgressBar.getIndeterminateDrawable().setColorFilter(Color.rgb(155,25,21), PorterDuff.Mode.MULTIPLY);
        textView = (TextView)findViewById(R.id.titre);
        ResultProgress rp=new ResultProgress();
        rp.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(RequestStrategy r : rqList) {
            if(r != null) {
                r.deleteTables();
            }
        }
    }
    @Override
    public void onBackPressed() {
        if(!desactive)
        {
            if(rqList != null) {
                for(RequestStrategy r : rqList) {
                    if(r != null) {
                        r.deleteTables();
                    }
                }
            }

            goToResultActivity = new Intent(ResultActivity.this, RequestActivity.class);
            goToResultActivity.putStringArrayListExtra("selectedDatabaseList", selectedDatabaseList);
            startActivity(goToResultActivity);
        }
    }
}
