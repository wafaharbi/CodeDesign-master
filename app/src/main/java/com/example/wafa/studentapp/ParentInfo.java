package com.example.wafa.studentapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class ParentInfo extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    FirebaseAuth  auth;
    FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference ref;
    String userID;
    FirebaseUser u;
    ListView listView;
    Button update;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_info);
        listView = (ListView) findViewById(R.id.listInfoParent);
        auth = FirebaseAuth.getInstance();
        u = auth.getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("Parents").child(u.getUid());

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

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                display(dataSnapshot);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }



    public  void display(DataSnapshot dataSnapshot){

       final Parent parents = new Parent();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {


            final  String name =parents.setName(dataSnapshot.child("name").getValue().toString());
            final  String username =parents.setUsername( dataSnapshot.child("username").getValue().toString());
            final   String email = parents.setEmail(dataSnapshot.child("email").getValue().toString());
            final   String password = parents.setPassword(dataSnapshot.child("password").getValue().toString());
            final    String phone =parents.setPhone(dataSnapshot.child("phone").getValue().toString());


            ArrayList<String> array = new ArrayList<>();
            array.add(name);
            array.add(username);
            array.add(email);
            array.add(password);
            array.add(phone);

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);

            listView.setAdapter(arrayAdapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Intent i = new Intent(getApplicationContext() , ParentUpadate.class);

                    i.putExtra("name",parents.getName());
                    i.putExtra("email", parents.getEmail());
                    i.putExtra("phone" ,parents.getPhone());
                    startActivity(i);
                }
            });



        }
    }

    public void signoutParent(View v){

        auth.signOut();
        Toast.makeText(getApplicationContext(), "Signout successfully" , Toast.LENGTH_SHORT).show();
        finish();
        Intent i = new Intent(this,LoginParent.class);
        startActivity(i);
    }

}

