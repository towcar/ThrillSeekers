package com.carsonskjerdal.app.thrillseekers.models;

/**
 * Created by Carson on 4/17/2018.
 * <p>
 * Feel free to use code just give credit please :)
 */
public class Places {
    public String name;
    public String lat;
    public String lon;
    public String group;

    public Places(String name, String lat, String lon, String group) {

        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.group = group;


    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        //Log.e("getName","Name is: " + name);
        return name;
    }


}

