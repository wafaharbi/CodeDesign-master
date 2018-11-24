package com.example.wafa.studentapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CourseStudentIT342 extends AppCompatActivity {

    private DatabaseReference mUserDatabase,currentUser;   //databaseReference

    private FirebaseUser mCurrentUser;
//Android Layout

    CircleImageView mDisplayImage;
    Button mNameBtn, mImageBtn , update;
    ListView listView;
    TextView Viewname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_student_it342);

        listView = (ListView) findViewById(R.id.listInfoStudent);


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        currentUser = FirebaseDatabase.getInstance().getReference().child("Student").child(current_uid);
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


        //Student
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Student").child(current_uid).child("IT342");
        mUserDatabase.keepSynced(true);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
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


        }
    }

}


