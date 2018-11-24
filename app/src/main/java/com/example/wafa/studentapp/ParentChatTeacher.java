package com.example.wafa.studentapp;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParentChatTeacher extends AppCompatActivity {


    RecyclerView mUserList;


    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_chat_teacher);
//Arrow to return back
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mUserList = (RecyclerView) findViewById(R.id.users_list);
        mUserList.setHasFixedSize(true);
        mUserList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Teachers");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<User , UserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(
                User.class,
                R.layout.user_single_item,
                UserViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, User model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setUserImage(model.getImage() , getApplicationContext());


                // __ To get ID for Specific user __ !
                final String user_id = getRef(position).getKey();


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(ParentChatTeacher.this, MessageParentTeacher.class);
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
