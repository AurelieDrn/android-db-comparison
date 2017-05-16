package com.nantes.polytech.netapsys.strategy;

import android.content.Context;
import android.support.test.espresso.core.deps.guava.io.Files;
import android.util.Log;

import com.nantes.polytech.netapsys.data.MemberData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static com.nantes.polytech.netapsys.AppConstants.EXEC_TIME;
import static com.nantes.polytech.netapsys.AppConstants.listMember;

/**
 * Created by Aurelie on 20/02/2017.
 */

public class RequestStrategyFile implements RequestStrategy {
    private static final String TAG = RequestStrategyFile.class.getSimpleName().toString();
    private Context context;
    private String memberFilename = "member";
    private File memberTable;

    public RequestStrategyFile(Context context) throws IOException {
        this.context = context;
        this.memberTable = new File(context.getFilesDir(), memberFilename);
        this.init();
    }

    @Override
    public void init() throws IOException {
        List<String> myListMember = new ArrayList<String>();

        for(MemberData mData : listMember) {
            String s = mData.getLastName()+" "+mData.getFirstName()+" "+mData.getAge()+" "+mData.getAddress()+" "+mData.getPhoneNumber();
            myListMember.add(s);
        }

        write(memberFilename, myListMember, this.context);
        //read(memberTable);
    }

    private static void write(String filename, List<String> data, Context context) {
        try {
            FileOutputStream outputStream;
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));

            for(String s : data) {
                bw.write(s);
                bw.newLine();
            }
            bw.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void read(File filename) throws IOException {
        BufferedReader br = null;
        Log.d(TAG, "READ");
        try {
            br = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = br.readLine()) != null) {
                Log.d(TAG, line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
    }

    @Override
    public long runSelectRequest() throws IOException {
        long startTime, endTime;
        List<Long> execTimeList = new ArrayList<Long>();
        BufferedReader br = new BufferedReader(new FileReader(memberTable));
        try {
            for(int i = 0; i < EXEC_TIME; i++) {
                br = new BufferedReader(new FileReader(memberTable));
                String line;
                //startTime = System.currentTimeMillis();
                startTime = System.nanoTime();
                while ((line = br.readLine()) != null) {
                    //Log.d(TAG, line);
                }
                //endTime = System.currentTimeMillis();
                endTime = System.nanoTime();
                execTimeList.add(endTime - startTime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }

        //this.read(memberTable);
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

        FileWriter fw = new FileWriter(memberTable.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);
        for(int i = 0; i < EXEC_TIME; i++) {
            //startTime = System.currentTimeMillis();
            startTime = System.nanoTime();
            bw.write("Dupuis Alexandrin 69 31 Rue Goya 01.19.98.13.38");
            bw.newLine();
            //endTime = System.currentTimeMillis();
            endTime = System.nanoTime();
            execTimeList.add(endTime-startTime);
        }
        bw.close();
        //this.read(memberTable);
        // vider le fichier et reinitialiser les donnees
        FileWriter fw2 = new FileWriter(memberTable);
        BufferedWriter bw2 = new BufferedWriter(fw2);
        bw2.write("");
        this.init();
        bw2.close();

        //this.read(memberTable);

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

        for(int i = 0; i < EXEC_TIME; i++) {
            //startTime = System.currentTimeMillis();
            startTime = System.nanoTime();
            FileReader fw = new FileReader(memberTable);
            BufferedReader bw = new BufferedReader(fw);

            // ecrire dans un second fichier
            File file2 = new File(context.getFilesDir(), "file2");
            PrintWriter writer = new PrintWriter(file2, "UTF-8");
            String line;

            while ((line = bw.readLine()) != null) {
                // trouver le prenom
                int cpt = 0;
                String s = "";
                for (char c : line.toCharArray()) {
                    if (c == ' ') {
                        cpt++;
                    } else if (cpt == 1) {
                        s += c;
                    }
                }
                // si le prenom est Bruno, le remplace par Martin
                if (s.equals("Clothilde")) {
                    String line2 = line.replaceAll("Clothilde", "Marie");
                    line = line2;
                }
                writer.println(line);
            }
            bw.close();
            if (writer.checkError())
                throw new IOException("Cannot write");
            writer.close();

            // mettre a jour le fichier
            Files.copy(file2, this.memberTable);

            // supprimer le fichier temporaire
            file2.delete();
            //endTime = System.currentTimeMillis();
            endTime = System.nanoTime();
            execTimeList.add(endTime-startTime);


            // vider le fichier et reinitialiser les donnees
            FileWriter fw2 = new FileWriter(memberTable);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            bw2.write("");
            this.init();
            bw2.close();
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

        FileWriter fw = new FileWriter(memberTable.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);
        for(int i = 0; i < EXEC_TIME; i++) {
            //startTime = System.currentTimeMillis();
            startTime = System.nanoTime();
            bw.write("Bourgeau Jeanette 37 91 rue Gustave Eiffel 04.95.61.98.76");
            bw.newLine();
            //endTime = System.currentTimeMillis();
            endTime = System.nanoTime();
            execTimeList.add(endTime-startTime);
        }
        bw.close();

        for(int i = 0; i < EXEC_TIME; i++) {
            //startTime = System.currentTimeMillis();
            startTime = System.nanoTime();
            FileReader fw2 = new FileReader(memberTable);
            BufferedReader bw2 = new BufferedReader(fw2);

            // ecrire dans un second fichier
            File file2 = new File(context.getFilesDir(), "file2");
            PrintWriter writer = new PrintWriter(file2, "UTF-8");
            String line;

            while ((line = bw2.readLine()) != null) {
                // trouver le prenom
                int cpt = 0;
                String s = "";
                for (char c : line.toCharArray()) {
                    if (c == ' ') {
                        cpt++;
                    } else if (cpt == 1) {
                        s += c;
                    }
                }
                // si le prenom est Bruno, le remplace par Martin
                if (s.equals("Clothilde") == false) {
                    writer.println(line);
                }
            }
            bw2.close();
            if (writer.checkError())
                throw new IOException("Cannot write");
            writer.close();

            // mettre a jour le fichier
            Files.copy(file2, this.memberTable);

            // supprimer le fichier temporaire
            file2.delete();
            //endTime = System.currentTimeMillis();
            endTime = System.nanoTime();
            execTimeList.add(endTime-startTime);


            // vider le fichier et reinitialiser les donnees pour relancer la requete
            FileWriter fw3 = new FileWriter(memberTable);
            BufferedWriter bw3 = new BufferedWriter(fw3);
            bw3.write("");
            this.init();
            bw3.close();
        }
        long sum = 0;
        for(Long t : execTimeList) {
            sum += t;
        }
        //this.read(memberTable);
        return sum/EXEC_TIME;
    }

    @Override
    public void deleteTables() {

    }

}
