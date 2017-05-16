package com.nantes.polytech.netapsys.ormlite.model;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import java.io.IOException;
import java.sql.SQLException;
/**
 * Created by HASSAN29 on 19/01/2017.
 */

public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    public static void main(String[] args) throws SQLException, IOException {

        // Provide the name of .txt file which we have already created and kept in res/raw directory
        writeConfigFile("ormlite_config.txt");
    }
}
