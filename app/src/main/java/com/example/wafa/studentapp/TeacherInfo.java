package com.example.wafa.studentapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeacherInfo extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    FirebaseAuth  auth;
    FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference databaseReference;
    String userID;
    FirebaseUser u;
    ListView listView;
    Button update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info);

        listView = (ListView) findViewById(R.id.listInfoTeacher);

        auth =FirebaseAuth.getInstance();
        u = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Teachers").child(u.getUid());
        FirebaseUser studentuser = auth.getCurrentUser();

        authStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser studentuser = firebaseAuth.getCurrentUser();
                if(studentuser != null){
                    //////////
                }
                else{
                    ////////////
                }
            }
        };

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                displayinfo(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    public  void displayinfo(DataSnapshot dataSnapshot){


        final User info = new User();


        for(DataSnapshot ds: dataSnapshot.getChildren()){


            final  String name =info.setName(dataSnapshot.child("name").getValue().toString());
            final   String email = info.setEmail(dataSnapshot.child("email").getValue().toString());
            final   String password = info.setPassword(dataSnapshot.child("password").getValue().toString());
            final    String phone =info.setPhone(dataSnapshot.child("phone").getValue().toString());


            ArrayList<String> array = new ArrayList<>();
            array.add(name);
            array.add(email);
            array.add(password);
            array.add(phone);

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);

            listView.setAdapter(arrayAdapter);





            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Intent i = new Intent(getApplicationContext() , TeacherUpdate.class);

                    i.putExtra("name",info.getName());
                    i.putExtra("email", info.getEmail());
                    i.putExtra("phone" ,info.getPhone());
                    startActivity(i);
                }
            });


        }
    }










    public void signout(View v){

        auth.signOut();
        Toast.makeText(getApplicationContext(), "Signout successfully" , Toast.LENGTH_SHORT).show();
        finish();
        Intent i = new Intent(this,LoginTeacher.class);
        startActivity(i);
    }


}


