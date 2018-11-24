package com.example.wafa.studentapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class EventMain extends AppCompatActivity {


    RecyclerView recyclerView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);



        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("                   Event ");


        recyclerView =findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference().child("event");
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
        FirebaseRecyclerAdapter<Event , UserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, EventMain.UserViewHolder>(
                Event.class,
                R.layout.event_single_item,
                EventMain.UserViewHolder.class,
                reference
        ) {


            @Override
            protected void populateViewHolder(EventMain.UserViewHolder viewHolder, final Event model, int position) {


                final String user_id = getRef(position).getKey();

                viewHolder.setName(model.getTitle());

                viewHolder.setDesc(model.getDesc());

                viewHolder.setUserImage(model.getImage() , getApplicationContext());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent i = new Intent(EventMain.this, EventDescription.class);
                        i.putExtra("user_id" , user_id);
                        i.putExtra("title" , model.getTitle());
                        i.putExtra("desc", model.getDesc());
                        i.putExtra("image" , model.getImage());
                        startActivity(i);
                    }
                });

                // __ To get ID for Specific user __ !//

            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class UserViewHolder extends  RecyclerView.ViewHolder {

        View mView;

        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        public void setName(String name) {

            TextView UserName = (TextView) mView.findViewById(R.id.title);
            UserName.setText(name);
        }

        public void setDesc(String desc) {

            TextView descr = (TextView) mView.findViewById(R.id.desc);
            descr.setText(desc);
        }

        public void setUserImage(String image, Context ctx) {

            ImageView userImageView = (ImageView) mView.findViewById(R.id.image);

            Picasso.with(ctx).load(image).placeholder(R.drawable.default_img).into(userImageView);
        }


    }

}


