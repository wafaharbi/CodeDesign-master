package com.example.wafa.studentapp;

public class Event {

    String title ,image ,  desc ;

    public Event(String title, String image, String desc) {
        this.title = title;
        this.image = image;
        this.desc = desc;
    }

    public  Event(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
