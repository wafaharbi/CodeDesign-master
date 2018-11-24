package com.example.wafa.studentapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class editStudentCS383 extends AppCompatActivity {

    DatabaseReference reference , currentUser ;

    ListView listView;
    Button btnEdit;

    TextView Viewname;
    public String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_cs383);


        btnEdit = (Button) findViewById(R.id.btnIT215);

        listView = (ListView) findViewById(R.id.listInfoStudent);


        user_id=   getIntent().getStringExtra("user_id");


        currentUser = FirebaseDatabase.getInstance().getReference().child("Student").child(user_id);



        Viewname = (TextView) findViewById(R.id.nameView);


        currentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User info = new User();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    final String name = info.setName(dataSnapshot.child("name").getValue().toString());

                    Viewname.setText(name);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        reference = FirebaseDatabase.getInstance().getReference().child("Student").child(user_id).child("CS383");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                showUserProfile(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showUserProfile(DataSnapshot dataSnapshot) {

        final User info = new User();


        for (DataSnapshot ds : dataSnapshot.getChildren()) {


            // final String name = info.setName(dataSnapshot.child("name").getValue().toString());
            final String attendance = info.setAttendance(dataSnapshot.child("attendance").getValue().toString());
            final String quiz = info.setQuize(dataSnapshot.child("quiz").getValue().toString());
            final String mid = info.setMid(dataSnapshot.child("mid").getValue().toString());
            final String finals = info.setFinals(dataSnapshot.child("finals").getValue().toString());
            //final String finals = dataSnapshot.child("image").getValue().toString();

            // Picasso.with(OtherStudentProfile.this).load(image).placeholder(R.drawable.default_img).into(mDisplayImage);

            ArrayList<String> array = new ArrayList<>();
            //array.add(name);
            array.add(attendance);
            array.add(quiz);
            array.add(mid);
            array.add(finals);

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);

            listView.setAdapter(arrayAdapter);



            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    Intent i = new Intent(getApplicationContext(), CS383MarkUpdate.class);

                    i.putExtra("user_id" , user_id);
                    //i.putExtra("attenda", info.getName());
                    i.putExtra("quiz", info.getQuize());
                    i.putExtra("mid", info.getMid());
                    i.putExtra("finals", info.getFinals());

                    startActivity(i);
                }
            });
        }
    }


}
