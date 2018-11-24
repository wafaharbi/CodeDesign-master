package com.example.wafa.studentapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class CourseViewInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view_info);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /// Student course ..

    public void GoToMaterial(View v){
        Intent i = new Intent(this, CourseViewTI473.class);
        startActivity(i);
    }

    public void GoToMaterialCS383(View v){
        Intent i = new Intent(this, CourseViewCS383.class);
        startActivity(i);
    }

    public void GoToMaterialIT443(View v){
        Intent i = new Intent(this, CourseViewIT443.class);
        startActivity(i);
    }

    public void GoToMaterialIT215(View v){
        Intent i = new Intent(this, CourseViewIT215.class);
        startActivity(i);
    }

    public void GoToMaterialIT342(View v){
        Intent i = new Intent(this, CourseViewIT342.class);
        startActivity(i);
    }
    public void GoToMaterialcs214(View v){
        Intent i = new Intent(this, CourseViewCS214.class);
        startActivity(i);
    }
}
