package com.mymovielib;

/**
 * Created by arthur on 22.12.2015.
 */
public class Directors {
    String Name;
    String Image;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Directors(String name, String image){
        this.Name = name;
        this.Image = image;
    }
}
