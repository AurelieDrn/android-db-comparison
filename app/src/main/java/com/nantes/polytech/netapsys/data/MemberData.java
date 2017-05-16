package com.nantes.polytech.netapsys.data;

/**
 * Created by Aurelie on 24/02/2017.
 */

public class MemberData {
    private String lastName;
    private String firstName;
    private int age;
    private String address;
    private String phoneNumber;

    public MemberData( String lastName, String firstName, int age, String address, String phoneNumber) {
        this.age = age;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getAge() {
        return age;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
