package com.example.wafa.studentapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class ContactUS extends AppCompatActivity {

    TextView contac_us  , link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);



        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        contac_us = ( TextView) findViewById(R.id.txtcontact);

        link =  ( TextView) findViewById(R.id.txtLinsView);

        contac_us.setText("we have developed this application" +"\n"  +
                "that helps both parents, educators, and students realize" +
                " their potential and for " +"\n"+ "creating a communication network " +"\n"+
                "to link between them." +
                " Also, this system"+"\n"+ "provides all" +
                "the services for students, parents, and teachers of the relevant departments" +
                " and we have provided it " +"\n"+ "and development" +
                "of an integrated"+"\n"+" system "
                +"for management of education affairs "+
                "for Qassim University.\n");


        link.setText("PAS.APP.2018@gmail.com");


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
