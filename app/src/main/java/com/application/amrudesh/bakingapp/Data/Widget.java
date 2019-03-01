package com.application.amrudesh.bakingapp.Data;

import java.util.ArrayList;

public class Widget {

    private String recipeName;
    private ArrayList<Ingredients> ingredientsArrayList;

    public Widget() {
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public ArrayList<Ingredients> getIngredientsArrayList() {
        return ingredientsArrayList;
    }

    public void setIngredientsArrayList(ArrayList<Ingredients> ingredientsArrayList) {
        this.ingredientsArrayList = ingredientsArrayList;
    }
}

