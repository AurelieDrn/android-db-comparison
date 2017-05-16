package com.nantes.polytech.netapsys.ormlite.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by HASSAN29 on 14/02/2017.
 */

public class Inscription {

    @DatabaseField(generatedId = true,columnName = "id")
    private int id;
    @DatabaseField(columnName = "id_member")
    private int idmember;
    @DatabaseField(columnName = "id_activity")
    private int idactivity;

    public Inscription()
    {
        this.idmember=0;
        this.idactivity=0;
    }

    public Inscription(int id1, int id2)
    {
        this.idmember=id1;
        this.idactivity=id2;
    }

    public int getId()
    {
        return this.id;
    }
    public int getIdMember()
    {
        return this.idmember;
    }
    public int getIdActivity()
    {
        return this.idactivity;
    }
    public void setIdMember(int id){this.idmember=id;}
    public void setIdActivity(int id){this.idactivity=id;}

    @Override
    public String toString()
    {
        return "Inscription[id: "+this.id+", membre: "+this.idmember+", activité: "+this.idactivity+"]";
    }
}

