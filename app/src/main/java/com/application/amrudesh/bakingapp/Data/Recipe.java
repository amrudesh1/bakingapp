package com.application.amrudesh.bakingapp.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {



    private int id;
    private String name;
    private String image;
    private Boolean tab;


    public Recipe(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.image = in.readString();
        this.tab = in.readByte()!=0;
    }
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeByte((byte) (tab ? 1 : 0));

    }
    public static  Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
    }
