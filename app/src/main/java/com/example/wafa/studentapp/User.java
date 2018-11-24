package com.example.wafa.studentapp;


public class User {

    public String name,email , password , phone , username  , image , thumb_image  ,id,course1,course2,course3,mark,
            coursename, attendance, quize, mid, finals;


    public User(String name, String email, String password,String username, String phone,String image , String thumb_image , String id,
               String course1,String course2,String course3, String mark){
        this.name= name;
        this.email= email;
        this.password= password;
        this.phone= phone;
        this.id=id;
        this.username=username;
        this.image=image;
        this.thumb_image= thumb_image;
        this.course1=course1;
        this.course2=course2;
        this.course3=course3;
        this.mark=mark;

    }

    public User(String name, String email, String password, String phone,String image , String thumb_image , String id,
     String course1,String course2,String course3, String mark){
        this.name= name;
        this.email= email;
        this.password= password;
        this.phone= phone;
        this.id = id;
        this.image=image;
        this.thumb_image= thumb_image;
        this.course1=course1;
        this.course2=course2;
        this.course3=course3;
        this.mark=mark;
    }

    public  User(String name , String email , String phone){
        this.name= name;
        this.email= email;
        this.phone= phone;



    }

    public  User(String name, String email, String password, String phone){
        this.name= name;
        this.email= email;
        this.password= password;
        this.phone= phone;
    }

    public User(){

    }


    public String getName() {
        return name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String setEmail(String email) {
        this.email = email;
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
        this.password = password;
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String setPhone(String phone) {
        this.phone = phone;
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String setUsername(String username) {
        this.username = username;
        return username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCourse1() {
        return course1;
    }

    public String setCourse1(String course1) {
        this.course1 = course1;
        return course1;
    }



    public String getCourse2() {
        return course2;
    }

    public void setCourse2(String course2) {
        this.course2 = course2;
    }




    public String getCourse3() {
        return course3;
    }

    public void setCourse3(String course3) {
        this.course3 = course3;
    }

    public String getMark() {
        return mark;
    }

    public String setMark(String mark) {
        this.mark = mark;
        return mark;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getAttendance() {
        return attendance;
    }

    public String setAttendance(String attendance) {
        this.attendance = attendance;
        return attendance;
    }

    public String getQuize() {
        return quize;
    }

    public String setQuize(String quize) {
        this.quize = quize;
        return quize;
    }

    public String getMid() {
        return mid;
    }

    public String setMid(String mid) {
        this.mid = mid;
        return mid;
    }

    public String getFinals() {
        return finals;
    }

    public String setFinals(String finals) {
        this.finals = finals;
        return finals;
    }
}
