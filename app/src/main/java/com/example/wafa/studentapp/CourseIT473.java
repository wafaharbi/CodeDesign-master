package com.example.wafa.studentapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CourseIT473 extends AppCompatActivity {

    RecyclerView mUserList;


    DatabaseReference mDatabase;

     /// this will be courses for Teacher

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_it473);


      // list of student that teacher teach them ....
        mUserList = (RecyclerView) findViewById(R.id.users_list);
        mUserList.setHasFixedSize(true);
        mUserList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Student");


    }


    @Override
    protected void onStart() {
        super.onStart();

    //// get student ..

        FirebaseRecyclerAdapter<User , StudentChatTeacher.UserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, StudentChatTeacher.UserViewHolder>(
                User.class,
                R.layout.user_single_item,
                StudentChatTeacher.UserViewHolder.class,
                mDatabase
        ) {
            @Override
            protected  void populateViewHolder(final StudentChatTeacher.UserViewHolder viewHolder, User model, int position) {



                // __ To get ID for Specific user __ !

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


                /// to seperate each id //
                final  String currntUser = firebaseUser.getUid();
                final String user_id = getRef(position).getKey();



                viewHolder.setName(model.getName());

                viewHolder.setUserImage(model.getImage() , getApplicationContext());






                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(CourseIT473.this, editStudentIT473.class);
                        i.putExtra("user_id" , user_id);
                        startActivity(i);
                    }
                });

            }
        };

        mUserList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class UserViewHolder extends  RecyclerView.ViewHolder{

        View mView;


        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }



        public void  setName(String name){

            TextView UserName = (TextView) mView.findViewById(R.id.user_single_name);


            UserName.setText(name);
        }

        public  void setUserImage(String image, Context ctx){

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.user_single_img);

            Picasso.with(ctx).load(image).placeholder(R.drawable.default_img).into(userImageView);
        }


    }


}
