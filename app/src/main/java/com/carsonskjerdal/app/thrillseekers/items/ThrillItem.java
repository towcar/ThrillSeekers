package com.carsonskjerdal.app.thrillseekers.items;

/**
 * Created by Carson on 4/13/2018.
 * <p>
 * Feel free to use code just give credit please :)
 */
public class ThrillItem {


    private String name;
    private Integer image;

    public ThrillItem(String name, int image) {

        this.name = name;
        this.image = image;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        //Log.e("getName","Name is: " + name);
        return name;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public Integer getImage() {
        return image;
    }

}

