package com.nantes.polytech.netapsys.data;

/**
 * Created by Aurelie on 24/02/2017.
 */

public class InscriptionData {
    int idMember;
    int idActivity;

    public InscriptionData(int idActivity, int idMember) {
        this.idActivity = idActivity;
        this.idMember = idMember;
    }

    public int getIdActivity() {
        return idActivity;
    }

    public int getIdMember() {
        return idMember;
    }
}
