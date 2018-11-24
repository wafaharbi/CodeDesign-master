package com.example.wafa.studentapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignupParent extends AppCompatActivity implements View.OnClickListener{

    EditText ParentName , ParentEmail , ParenntPassword , ParentPhone , Parentusername ;
    ImageView signup;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    private ProgressDialog progressDialog;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_parent);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("       ");

        ParentName = (EditText) findViewById( R.id.editName);
        ParentEmail= (EditText) findViewById(R.id.editElogin);
        ParenntPassword = (EditText) findViewById(R.id.editPlogin);
        ParentPhone = (EditText) findViewById(R.id.editPhone);
        Parentusername = (EditText) findViewById(R.id.editUsername) ;
        signup = (ImageView) findViewById(R.id.textViewSignup);

        firebaseUser =FirebaseAuth.getInstance().getCurrentUser();

        signup.setOnClickListener(this);
        auth= FirebaseAuth.getInstance();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void Account(View v){
        Intent i = new Intent(this, LoginParent.class);
        startActivity(i);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser() != null){

        }
    }

    public void register(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing your request, please wait...");

        progressDialog.show();

        final String name =  ParentName.getText().toString().trim();
        final String username = Parentusername.getText().toString().trim();
        final String email = ParentEmail.getText().toString().trim();
        final String password = ParenntPassword.getText().toString().trim();
        final String phone = ParentPhone.getText().toString().trim();
        final String image = "default";
        final  String thumb_image = "default";
        final String course1="default";
        final String course2="default";
        final String course3="default";
        final String mark="default";

        if(name.isEmpty()){
            ParentName.setError("Please enter your name");
            ParentName.requestFocus();
            return;
        }
        if(username.isEmpty()){
            Parentusername.setError("Please enter your username");
            Parentusername.requestFocus();
            return;
        }
        if(email.isEmpty()){
            ParentEmail.setError("Please enter your email");
            ParentEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ParentEmail.setError("Invalid email");
            ParentEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            ParenntPassword.setError("Please enter your password");
            ParenntPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            ParenntPassword.setError("password at least 6 numbers");
            ParenntPassword.requestFocus();
            return;
        }

        if(phone.isEmpty()){
            ParentPhone.setError("Please enter your phone numbers");
            ParentPhone.requestFocus();
            return;
        }
        if(phone.length() != 10){

            ParentPhone.setError("phone at least 10 numbers");
            ParentPhone.requestFocus();
            return;

        }





        firebaseDatabase = FirebaseDatabase.getInstance();

        ref= firebaseDatabase.getReference().child("Parents");

        Query usernameQuery  = FirebaseDatabase.getInstance().getReference().child("Student").orderByChild("username").equalTo(username);

        usernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0) {

                    Toast.makeText(getApplicationContext(), "correct username ", Toast.LENGTH_SHORT).show();

                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                final  String id= firebaseUser.getUid();

                                User parent= new User(name,email,password,phone ,username , image , thumb_image, id,course1,course2,course3 ,mark);

                                HashMap<String, String> userMap = new HashMap<>();

                                userMap.put("name", name);
                                userMap.put("username" , username);
                                userMap.put("email" , email);
                                userMap.put("password" , password);
                                userMap.put("phone" , phone);
                                userMap.put("id" ,id);
                                userMap.put("image", "default");
                                userMap.put("thumb_image", "default");


                                //////////new ////////
                                //// do not need ....
                                userMap.put("course1", "default");
                                userMap.put("course2", "default");
                                userMap.put("course3", "default");
                                userMap.put("mark", "default");
                                //////////new stop ////////


                                FirebaseDatabase.getInstance().getReference("Parents").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {


                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){

                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext()," Parent signup successflly" , Toast.LENGTH_SHORT).show();
                                            finish();
                                            Intent i = new Intent(getApplicationContext() , ParentHome.class);
                                            startActivity(i);

                                        }
                                        else {
                                            progressDialog.dismiss();
                                            if( task.getException() instanceof FirebaseAuthUserCollisionException)
                                            {
                                                Toast.makeText(getApplicationContext()," You are already signin " , Toast.LENGTH_SHORT).show();

                                            }
                                            else {
                                                progressDialog.dismiss();
                                                Toast.makeText(getApplicationContext(), " Please try again", Toast.LENGTH_SHORT).show();

                                            }

                                        }
                                    }
                                });
                            }
                            else{
                                progressDialog.dismiss();

                                Toast.makeText(getApplicationContext(), "an error accoured , Please try again", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });



                }
                else{

                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), "choose different username " , Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textViewSignup:
                register();

                break;


        }

    }


}
