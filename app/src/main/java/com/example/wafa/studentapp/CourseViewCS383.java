package com.example.wafa.studentapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CourseViewCS383 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view_cs383);
    }

    public void filesUploads(View v ){

        Intent i = new Intent(CourseViewCS383.this, ViewUploadsActivity.class);
        startActivity(i);
    }

    public void ViewInfo(View v ){

        Intent i = new Intent(CourseViewCS383.this, CourseStudentCS383.class);
        startActivity(i);
    }
}
