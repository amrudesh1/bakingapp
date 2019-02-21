package com.application.amrudesh.bakingapp.Data;

import java.io.Serializable;

public class Steps implements Serializable {
    private String id;
    private String short_description;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public Steps(String id, String short_description, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.short_description = short_description;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public Steps()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
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
}
