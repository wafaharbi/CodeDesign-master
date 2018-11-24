package com.example.wafa.studentapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MeassageTeacherParent extends AppCompatActivity {

    TextView id , nameofuser;
    ImageButton sendbtn;
    EditText sendtxt;
    FirebaseUser fuser;
    DatabaseReference reference , currentUser;
    Toolbar toolbar;
    UserAdapter userAdapter;
    List<Chat> mChat;
    RecyclerView recyclerView;
    DatabaseReference ref;
    CircleImageView mDisplayImage;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meassage_teacher_parent);
// hide
        getSupportActionBar().hide();

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        final String user_id=   getIntent().getStringExtra("user_id");

        reference = FirebaseDatabase.getInstance().getReference().child("Parents").child(user_id);
        id =(TextView) findViewById(R.id.nameuser);
        mDisplayImage = (CircleImageView) findViewById(R.id.umg_user);

        nameofuser = (TextView) findViewById(R.id.name);
        sendbtn  =(ImageButton) findViewById(R.id.btn_send);
        sendtxt = (EditText) findViewById(R.id.txt_send);

        ImageButton back = (ImageButton)findViewById(R.id.backToMain);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = FirebaseDatabase.getInstance().getReference().child("Teachers");
        id.setText(user_id);

        nameofuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext() , OtherParentProfile.class);
                i.putExtra("user_id" , user_id);
                startActivity(i);

            }
        });


        //Arrow back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message= sendtxt.getText().toString();
                String date = sendtxt.getText().toString();

                if(!message.equals("")){
                    sendMessage(fuser.getUid(),user_id , message , date);
                }
                else{
                    Toast.makeText(getApplicationContext() , " you can not send empty message .." , Toast.LENGTH_SHORT).show();
                }
                sendtxt.setText("");
            }
        });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                final String name = dataSnapshot.child("name").getValue().toString();

                final String image = dataSnapshot.child("image").getValue().toString();

                Picasso.with(MeassageTeacherParent.this).load(image).placeholder(R.drawable.default_img).into(mDisplayImage);
                nameofuser.setText(name);

                readMessage(fuser.getUid() , user_id);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private  void sendMessage(String sender , String reciver , String msg,  String date){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        date = DateFormat.getDateTimeInstance().format(new Date());

        HashMap<String , Object> hashMap  =new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("reciver", reciver);
        hashMap.put("message", msg);
        hashMap.put("time" , ServerValue.TIMESTAMP);
        hashMap.put("date", date);

        ref.child("Chats").push().setValue(hashMap);
    }



    private void readMessage(final String myid , final String userid ){

        mChat = new ArrayList<>();
        DatabaseReference  Ref= FirebaseDatabase.getInstance().getReference().child("Chats");

        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mChat.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Chat chat = snapshot.getValue(Chat.class);

                    if(chat.getReciver().toString().equals(myid) && chat.getSender().toString().equals(userid)||
                            chat.getReciver().toString().equals(userid) && chat.getSender().toString().equals(myid)){

                        mChat.add(chat);
                    }
                    userAdapter = new UserAdapter(MeassageTeacherParent.this  , mChat );
                    recyclerView.setAdapter(userAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
