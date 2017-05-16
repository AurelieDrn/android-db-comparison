package com.nantes.polytech.netapsys.realm.model;

import io.realm.RealmObject;

/**
 * Created by Aurelie on 07/04/2017.
 */

public class Member extends RealmObject {

    private String lastName;
    private String firstName;
    private int age;
    private String address;
    private String phoneNumber;

    public Member() {

    }

    public Member(String lastName, String firstName, int age, String address, String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
