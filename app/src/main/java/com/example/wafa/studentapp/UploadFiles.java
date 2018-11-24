package com.example.wafa.studentapp;

/**
 * Created by Belal on 8/25/2017.
 */
public class UploadFiles {

    public String name;
    public String url;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public UploadFiles() {
    }

    public UploadFiles(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}