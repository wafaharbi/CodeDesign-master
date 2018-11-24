package com.example.wafa.studentapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class CourseNames extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_names);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    /// when teacher select the course go ..

    public void GoToMaterial(View v){
        Intent i = new Intent(this, CourseSelectTI473.class);
        startActivity(i);
    }

    public void GoToMaterialCS383(View v){
        Intent i = new Intent(this, CourseSelectCS383.class);
        startActivity(i);
    }

    public void GoToMaterialIT443(View v){
        Intent i = new Intent(this, CourseSlectIT443.class);
        startActivity(i);
    }

    public void GoToMaterialIT215(View v){
        Intent i = new Intent(this, CourseSelectIT215.class);
        startActivity(i);
    }

    public void GoToMaterialIT342(View v){
        Intent i = new Intent(this, CourseSelectIT342.class);
        startActivity(i);
    }
    public void GoToMaterialcs214(View v){
        Intent i = new Intent(this, CourseSelectCS214.class);
        startActivity(i);
    }
}

