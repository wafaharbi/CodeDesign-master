package com.example.wafa.studentapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseParentIT443 extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser parentUser, StudentUser;
    DatabaseReference currentRef , studentRef;
    ListView listView;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_parent_it443);


        listView = (ListView) findViewById(R.id.listInfoStudent);
        auth= FirebaseAuth.getInstance();
        parentUser = auth.getCurrentUser();

        currentRef = FirebaseDatabase.getInstance().getReference().child("Parents").child(parentUser.getUid());

        studentRef = FirebaseDatabase.getInstance().getReference().child("Student");

        String uid = FirebaseAuth.getInstance().getUid();

        currentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username = dataSnapshot.child("username").getValue(String.class);
                System.out.println(username);

                studentRef = FirebaseDatabase.getInstance().getReference().child("Student");

                Query studentQuery = studentRef.orderByChild("username").equalTo(username);
                studentQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot studentSnapshot: dataSnapshot.getChildren()) {
                            String key = studentSnapshot.getKey();
                            String email = studentSnapshot.child("email").getValue(String.class);
                            System.out.println(key+": "+email);

                            show(studentSnapshot);


                           /* Intent i = new Intent(getApplicationContext(), ParentHome.class);
                            startActivity(i);
                            Log.v("*****************" ,"###############");*/
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        throw databaseError.toException(); // don't ignore errors
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException(); // don't ignore errors
            }
        });




    }


    public  void show(DataSnapshot dataSnapshot){

        final User info = new User();

        for(DataSnapshot ds: dataSnapshot.getChildren()) {

            final String name = info.setUsername(dataSnapshot.child("name").getValue().toString());
            final String attendance = info.setAttendance(dataSnapshot.child("IT443").child("attendance").getValue().toString());
            final String quiz = info.setQuize(dataSnapshot.child("IT443").child("quiz").getValue().toString());
            final String mid = info.setMid(dataSnapshot.child("IT443").child("mid").getValue().toString());
            final String finals = info.setFinals(dataSnapshot.child("IT443").child("finals").getValue().toString());



            ArrayList<String> array = new ArrayList<>();

            array.add(name);
            array.add(attendance);
            array.add(quiz);
            array.add(mid);
            array.add(finals);

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
            listView.setAdapter(arrayAdapter);

        }
    }
}
