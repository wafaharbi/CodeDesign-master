package com.example.wafa.studentapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherParentProfile extends AppCompatActivity {


    FirebaseDatabase mFirebaseDatabase;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference databaseReference;
    String userID;
    FirebaseUser u;
    ListView listView;
    Button update;
    private ImageView mProfileImage;
    TextView id ;
    CircleImageView mDisplayImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_parent_profile);
//Arrow to return back
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listInfoStudent);

        final String user_id=   getIntent().getStringExtra("user_id");

        id =(TextView) findViewById(R.id.idProfile);


        databaseReference= FirebaseDatabase.getInstance().getReference().child("Parents").child(user_id);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                showUserProfile(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showUserProfile(DataSnapshot dataSnapshot) {

        final User info = new User();


        for (DataSnapshot ds : dataSnapshot.getChildren()) {


            final String name = info.setName(dataSnapshot.child("name").getValue().toString());
          //  final String username = info.setUsername(dataSnapshot.child("username").getValue().toString());
            final String email = info.setEmail(dataSnapshot.child("email").getValue().toString());
           // final String password = info.setPassword(dataSnapshot.child("password").getValue().toString());
         //   final String phone = info.setPhone(dataSnapshot.child("phone").getValue().toString());


            ArrayList<String> array = new ArrayList<>();
            array.add(name);
            //array.add(username);
            array.add(email);
          //  array.add(password);
          //  array.add(phone);

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);

            listView.setAdapter(arrayAdapter);


        }
    }

}

