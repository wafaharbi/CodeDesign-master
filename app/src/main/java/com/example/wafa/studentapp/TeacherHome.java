package com.example.wafa.studentapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



private DrawerLayout drawer;
    FirebaseAuth auth;
    Toolbar toolbar;

    CircleImageView profile;
    TextView nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        profile = findViewById(R.id.img_profile);
        nameView = findViewById(R.id.name_profile);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        FirebaseUser fuser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Teachers").child(fuser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final User info = new User();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    final String name = info.setName(dataSnapshot.child("name").getValue().toString());
                    final String image = dataSnapshot.child("image").getValue().toString();

                    nameView.setText(name);
                    Picasso.with(TeacherHome.this).load(image).placeholder(R.drawable.default_img).into(profile);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MessageFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MessageFragment()).commit();
                break;
            case R.id.nav_note:

                Intent i = new Intent(this, Notes.class);
                startActivity(i);


                break;

            case R.id.nav_profile:


                //ProfileActivity  =StudentInfo
                Intent x = new Intent(this, TeacherProfile.class);
                startActivity(x);
                break;

            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_out:

                FirebaseAuth.getInstance().signOut();

                // auth.signOut();
                Toast.makeText(getApplicationContext(), "Signout successfully" , Toast.LENGTH_SHORT).show();
                finish();
                Intent v = new Intent(this,LoginTeacher.class);
                startActivity(v);
                /*       Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();*/
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    public void GoToCourses(View v){
        Intent i = new Intent(this, CourseNames.class);   //FilesUpload
        startActivity(i);
    }


    public void chat(View v){

        Intent k = new Intent(this, TeacherListChats.class);
        startActivity(k);
    }



    public void University(View v){

        Intent m = new Intent(this, University.class);
        startActivity(m);
    }

    public void goToEvent(View v){

        Intent k = new Intent(this, EventMain.class);
        startActivity(k);
    }
    public void goToContactUs(View v){

        Intent k = new Intent(this, ContactUS.class);
        startActivity(k);
    }
    public void goToSchedule(View v){

        Intent k = new Intent(this, ScheduleMain.class);
        startActivity(k);
    }
}
