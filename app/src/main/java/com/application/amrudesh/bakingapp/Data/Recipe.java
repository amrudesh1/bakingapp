package com.application.amrudesh.bakingapp.Data;

import java.io.Serializable;

public class Recipe implements Serializable {

    private int id;
    private String name;
    private String image;
    private Boolean tab;

    public Recipe()
    {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getTab() {
        return tab;
    }

    public void setTab(Boolean tab) {
        this.tab = tab;
    }
}
