package com.example.wafa.studentapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CourseViewIT215 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view_it215);
    }



    //////  انوار غيرت فقط الاكتفتي تبع الfile upload بدل ماهو يروح له خليته يروح لView بس
    public void filesUploads(View v ){

        Intent i = new Intent(CourseViewIT215.this, ViewUploadsActivity.class);
        startActivity(i);
    }

    public void ViewInfo(View v ){

        Intent i = new Intent(CourseViewIT215.this, CourseStudentIT215.class);
        startActivity(i);
    }
}
