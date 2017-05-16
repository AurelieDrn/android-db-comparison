package com.nantes.polytech.netapsys.sqlite.model;

/**
 * Created by HASSAN29 on 15/11/2016.
 */


public class Activity {

    public static final String ACTIVITY_TABLE="activite";
    public static final String ID_COLUMN="ida";
    public static final String NAME_COLUMN="nom";
    public static final String ACTIVITY_PLACE="lieu";
    public static final String ACTIVITY_DESCRIPTION="description";
    public static final String ACTIVITY_DATE="date";

    private int id;
    private String name;
    private String place;
    private String description;
    private String date;
    //public SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public Activity()
    {
        this.id=0;
        this.name="unknown";
        this.place="unknown";
        this.description="unknown";
        this.date="01/01/1900";
    }
    public Activity(int id,String name, String place, String description, String date)
    {
        this.id=id;
        this.name=name;
        this.place=place;
        this.description=description;
        this.date=date;
    }
    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id=id;
    }

    public String getName()
    {return this.name;}
    public void setName(String name)
    { this.name=name;}

    public String getPlace()
    {return this.place;}
    public void setPlace(String place)
    {this.place=place;}

    public String getDescription()
    {return this.description;}
    public void setDescription(String description)
    {this.description=description;}

    public String getDate()
    {return this.date;}
    public void setDate(String date)
    {this.date=date;}

    @Override
    public String toString()
    {
        /*id: "+this.id+", */
        return "Activité[id:"+this.id+", nom: "+this.name+", place: "+this.place+", description: "+this.description+", date: "+this.date+"]";
    }
}

