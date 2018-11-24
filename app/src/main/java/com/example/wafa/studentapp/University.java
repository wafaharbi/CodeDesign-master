package com.example.wafa.studentapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class University extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);
        //Arrow to return back
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        TextView txt = (TextView) findViewById(R.id.textView18);
        txt.setMovementMethod(LinkMovementMethod.getInstance());

    }


    public void call(View v){

        String number ="016 380 0050";

        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:"+ number));
        startActivity(i);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
