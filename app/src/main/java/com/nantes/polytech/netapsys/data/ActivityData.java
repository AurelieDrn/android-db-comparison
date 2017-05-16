package com.nantes.polytech.netapsys.data;

/**
 * Created by Aurelie on 24/02/2017.
 */

public class ActivityData {
    private String name;
    private String place;
    private String description;
    private String date;

    public ActivityData(String date, String description, String name, String place) {
        this.date = date;
        this.description = description;
        this.name = name;
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getPlace() {
        return place;
    }

    public String getName() {
        return name;
    }
}
