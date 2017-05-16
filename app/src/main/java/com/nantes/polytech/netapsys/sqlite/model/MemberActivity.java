package com.nantes.polytech.netapsys.sqlite.model;

/**
 * Created by HASSAN29 on 24/11/2016.
 */

public class MemberActivity {
    private String nomMember;
    private String prenomMember;
    private String nomActivity;
    //private String dateActivity;
   // private Member member;
    //private Activity activity;

    public MemberActivity()
    {

    }

    /*public Member getMember()
    {
        return this.member;
    }
    public Activity getActivity()
    {
        return  this.activity;
    }

    public void setIdMember(int id)
    {
        this.member.setId(id);
    }
    */
    public void setLastname(String lastname)
    {
        this.nomMember=lastname;
    }

    public String getLastname()
    {
        return  this.nomMember;
    }
    public void setFirstname(String firstname)
    {
        this.prenomMember=firstname;
    }
    public String getFirstname()
    {
        return  this.prenomMember;
    }
    /*public void setAge(int age)
    {
        this.member.setAge(age);
    }
    public void setAddress(String address)
    {
        this.member.setAddress(address);
    }
    public void setTel(String tel)
    {
        this.member.setTel(tel);
    }
    public void setActivityId(int id)
    {
        this.activity.setId(id);
    }
    */
    public void setActivityName(String name)
    { this.nomActivity=name;}
    public String getActivityName()
    {
        return this.nomActivity;
    }
    /*public void setActivityPlace(String place)
    {this.activity.setPlace(place);}
    public void setActivityDescription(String description)
    {this.activity.setDescription(description);}
    */
    /*public String getDateActivity()
    {
        return this.dateActivity;
    }
    public void setActivityDate(String date)
    {this.dateActivity=date;}*/
}
