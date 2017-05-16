package com.nantes.polytech.netapsys.strategy;


import java.io.IOException;

/**
 * Created by HASSAN29 on 25/11/2016.
 */

public interface RequestStrategy {
    void init() throws IOException; //initialisation des bases de données
    long runSelectRequest() throws IOException;
    long runInsertRequest() throws IOException;
    long runUpdateRequest() throws IOException;
    long runDeleteRequest() throws IOException;
    void deleteTables();
}
