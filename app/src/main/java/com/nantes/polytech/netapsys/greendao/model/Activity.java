package com.nantes.polytech.netapsys.greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Aurelie on 21/12/2016.
 */

@Entity
public class Activity {

    @Id(autoincrement = true) private Long id;
    private String name;
    private String place;
    private String description;
    private String date;

    public Activity(String date, String place, String name, String description) {
        this.date = date;
        this.place = place;
        this.name = name;
        this.description = description;
    }

    @Generated(hash = 1168027003)
    public Activity(Long id, String name, String place, String description,
            String date) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.description = description;
        this.date = date;
    }

    @Generated(hash = 126967852)
    public Activity() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "date='" + date + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
