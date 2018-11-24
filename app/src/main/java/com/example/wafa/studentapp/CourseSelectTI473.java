package com.example.wafa.studentapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CourseSelectTI473 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_select_ti473);
    }

    public void filesUploads(View v ){

        Intent i = new Intent(CourseSelectTI473.this, FilesUpload.class);
        startActivity(i);
    }

    public void listStudent(View v ){

        Intent i = new Intent(CourseSelectTI473.this, CourseIT473.class);
        startActivity(i);
    }



}
