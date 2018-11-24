 package com.example.wafa.studentapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

 public class CourseViewTI473 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view_ti473);
    }

     public void filesUploads(View v ){

         Intent i = new Intent(CourseViewTI473.this, ViewUploadsActivity.class);
         startActivity(i);
     }

     public void ViewInfo(View v ){

         Intent i = new Intent(CourseViewTI473.this, CourseStudentIT473.class);
         startActivity(i);
     }
}
