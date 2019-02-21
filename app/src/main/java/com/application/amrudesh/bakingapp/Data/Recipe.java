package com.application.amrudesh.bakingapp.Data;

import java.io.Serializable;

public class Recipe implements Serializable {

    private String id;
    private String name;
    private String image;

    public Recipe()
    {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
