package com.mymovielib;

/**
 * Created by Артур on 12.12.2015.
 */
public class GridItem {
    private String image;
    private String title;

    public GridItem() {
        super();
    }

    public String getImage() {
      //  return "http://t2.gstatic.com/images?q=tbn:ANd9GcQW3LbpT94mtUG1PZIIzJNxmFX399wr_NcvoppJ82k7z99Hx6in";
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}