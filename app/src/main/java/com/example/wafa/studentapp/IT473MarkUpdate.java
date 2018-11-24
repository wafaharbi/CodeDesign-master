package com.example.wafa.studentapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IT473MarkUpdate extends AppCompatActivity {

    FirebaseAuth auth;
    TextView key;
    DatabaseReference ref;
    FirebaseUser u;
    EditText quiz, mid, finals;

    Button update , delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_it473_mark_update);

        quiz = (EditText) findViewById(R.id.updateQizeMark);
        mid = (EditText) findViewById(R.id.updateMidUpdate);
        finals = (EditText) findViewById(R.id.updateFinalMark);


        final String user_id=   getIntent().getStringExtra("user_id");

        ref = FirebaseDatabase.getInstance().getReference().child("Student").child(user_id).child("IT473");


        quiz.setText(getIntent().getStringExtra("quiz"));
        mid.setText(getIntent().getStringExtra("mid"));
        finals.setText(getIntent().getStringExtra("finals"));


    }


    public  void update(View v){




        String quizMark = quiz.getText().toString().trim();
        String midMark = mid.getText().toString().trim();
        String finalMark = finals.getText().toString().trim();


        //final Student user =new Student(quizMark,midMark,finalMark);


        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // here is update code

                // ref.child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);


                dataSnapshot.getRef().child("quiz").setValue(quiz.getText().toString());
                dataSnapshot.getRef().child("mid").setValue(mid.getText().toString());
                dataSnapshot.getRef().child("finals").setValue(finals.getText().toString());

                Toast.makeText(getApplicationContext() , " data was updated" , Toast.LENGTH_SHORT).show();

                IT473MarkUpdate.this.finish();
/*
                Intent i =new Intent(getApplicationContext(), editStudentIT215.class);
                startActivity(i);*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public  void delete(View v){

        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){


                    Toast.makeText(getApplicationContext() , " data was deleted " , Toast.LENGTH_SHORT).show();
                    //    StudentUpdateInfo.this.finish();

                }
                else{
                    Toast.makeText(getApplicationContext() , " data not deleted " , Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
