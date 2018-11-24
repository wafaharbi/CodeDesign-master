package com.example.wafa.studentapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CourseSelectCS214 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_select_cs214);
    }

    public void filesUploads(View v ){

        Intent i = new Intent(CourseSelectCS214.this, FilesUpload.class);
        startActivity(i);
    }

    public void listStudent(View v ){

        Intent i = new Intent(CourseSelectCS214.this, CourseCS214.class);
        startActivity(i);
    }
}
