package com.nantes.polytech.netapsys.sqlite.model;

/**
 * Created by HASSAN29 on 15/11/2016.
 */

public class Member {
    public static final String MEMBER_TABLE="membre";
    public static final String ID_COLUMN="idm";
    public static final String NAME_COLUMN="nom";
    public static final String MEMBER_FNAME ="prenom";
    public static final String MEMBER_AGE="age";
    public static final String MEMBER_ADDRESS="adresse";
    public static final String MEMBER_TEL="tel";

    private int id;
    private String lastname;
    private String firstname;
    private int age;
    private String address;
    private String tel;

    public Member()
    {
        this.lastname="unknown";
        this.firstname="unknown";
        this.age=0;
        this.address="unknown";
        this.tel="unknown";
    }
    public Member(int id, String lastname, String firstname, int age, String address, String tel)
    {
        super();
        this.id=id;
        this.lastname=lastname;
        this.firstname=firstname;
        this.age=age;
        this.address=address;
        this.tel=tel;

    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id=id;
    }

    public String getLastname()
    {
        return this.lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname=lastname;
    }
    public String getFirstname()
    {
        return this.firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname=firstname;
    }
    public int getAge()
    {
        return this.age;
    }

    public void setAge(int age)
    {
        this.age=age;
    }
    public String getAddress()
    {
        return this.address;
    }

    public void setAddress(String address)
    {
        this.address=address;
    }
    public String getTel()
    {
        return this.tel;
    }

    public void setTel(String tel)
    {
        this.tel=tel;
    }

    @Override
    public String toString()
    {
        return "Membre[id: "+this.id+", nom: "+this.lastname+", prenom: "+this.firstname+", age: "+this.age
                +", adresse: "+this.address+", tel: "+this.tel+"]";
    }
}
