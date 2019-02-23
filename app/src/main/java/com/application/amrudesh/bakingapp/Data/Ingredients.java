package com.application.amrudesh.bakingapp.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Ingredients implements Parcelable {

    private String quantity;
    private String measure;
    private String ingredient;

    public Ingredients(String quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }


    public Ingredients (Parcel in){
        this.quantity = in.readString();
        this.measure= in.readString();
        this.ingredient=in.readString();
    }
    public Ingredients() {

    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    public final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients(in);
        }

        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };
}