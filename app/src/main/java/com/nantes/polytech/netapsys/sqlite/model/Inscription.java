package com.nantes.polytech.netapsys.sqlite.model;

/**
 * Created by HASSAN29 on 17/11/2016.
 */

public class Inscription {
    public static final String INSCRIPTION_TABLE="inscription";
    public static final String INSCRIPTION_IDMEMBER="idmembre";
    public static final String INSCRIPTION_IDACTIVITY="idactivite";

    private int idmember;
    private int idactivity;

    public Inscription()
    {

    }
    public Inscription(int id1, int id2)
    {
        this.idmember=id1;
        this.idactivity=id2;
    }
    public int getIdMember()
    {
        return this.idmember;
    }

    public void setIdMember(int id)
    {
        this.idmember=id;
    }

    public int getIdActivity()
    {
        return this.idactivity;
    }

    public void setIdActivity(int id)
    {
        this.idactivity=id;
    }
    @Override
    public String toString()
    {
        return "Inscription[id membre: "+this.idmember+", id activité: "+this.idactivity+"]";
    }

}
