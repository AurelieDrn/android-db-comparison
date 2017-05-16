package com.nantes.polytech.netapsys.ormlite.model;
import com.j256.ormlite.field.DatabaseField;
/**
 * Created by HASSAN29 on 16/01/2017.
 */

public class Member {
    @DatabaseField(generatedId = true,columnName = "member_id")
    private int id;
    @DatabaseField(columnName = "member_lastname")
    private String lastname;
    @DatabaseField(columnName = "member_firstname")
    private String firstname;
    @DatabaseField(columnName = "member_age")
    private int age;
    @DatabaseField(columnName = "member_address")
    private String address;
    @DatabaseField(columnName = "member_tel")
    private String tel;

    public Member()
    {
        this.lastname="unknown";
        this.firstname="unknown";
        this.age=0;
        this.address="unknown";
        this.tel="unknown";
    }
    public Member(String lastname, String firstname, int age, String address, String tel)
    {
        //super();
       // this.id=id;
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
