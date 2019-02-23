package com.application.amrudesh.bakingapp.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class Steps implements Parcelable {


    private String shortdescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public Steps() {

    }

    public Steps(String shortdescription, String description, String videoURL, String thumbnailURL) {
        this.shortdescription = shortdescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public Steps(Parcel in) {
        this.shortdescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
    }

    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shortdescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    public final Parcelable.Creator CREATOR = new Creator<Steps>() {
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };
}
