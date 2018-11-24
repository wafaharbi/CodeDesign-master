package com.example.wafa.studentapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupTeacher extends AppCompatActivity implements View.OnClickListener {

    EditText TeacherName, TeacherEmail, TeacherPassword, TeacherPhone;
    ImageView signup;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    private ProgressDialog progressDialog;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_teacher);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("       ");
        TeacherName = (EditText) findViewById(R.id.editName);
        TeacherEmail = (EditText) findViewById(R.id.editElogin);
        TeacherPassword = (EditText) findViewById(R.id.editPlogin);
        TeacherPhone = (EditText) findViewById(R.id.editPhone);
        signup = (ImageView) findViewById(R.id.textViewSignup);

        firebaseUser =FirebaseAuth.getInstance().getCurrentUser();

        signup.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();


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
        Intent i = new Intent(this, LoginTeacher.class);
        startActivity(i);
    }


    @Override
    protected void onStart() {

        super.onStart();
        if (auth.getCurrentUser() != null) {

        }
    }

    public void register() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing your request, please wait...");

        progressDialog.show();


        final String name = TeacherName.getText().toString().trim();
        final String email = TeacherEmail.getText().toString().trim();
        final String password = TeacherPassword.getText().toString().trim();
        final String phone = TeacherPhone.getText().toString().trim();
        final String img = "default";
        final  String thumb_img = "default";

        //////////new ////////

        final String course1="default";
        final String course2="default";
        final String course3="default";
        final String mark="default";

        //////////new stop ////////

        if (name.isEmpty()) {
            TeacherName.setError("Please enter your name");
            TeacherName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            TeacherEmail.setError("Please enter your email");
            TeacherEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            TeacherEmail.setError("Invalid email");
            TeacherEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            TeacherPassword.setError("Please enter your password");
            TeacherPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            TeacherPassword.setError("password at least 6 numbers");
            TeacherPassword.requestFocus();
            return;
        }


        if (phone.isEmpty()) {
            TeacherPhone.setError("Please enter your phone");
            TeacherPhone.requestFocus();
            return;
        }
        if (phone.length() != 10) {

            TeacherPhone.setError("phone at least 10 numbers");
            TeacherPhone.requestFocus();
            return;

        }

        firebaseDatabase = FirebaseDatabase.getInstance();

        ref = firebaseDatabase.getReference().child("Teachers");


        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    final  String id= firebaseUser.getUid();

                    User teacher = new User(name, email,phone,password,img, thumb_img , id,course1,course2,course3,mark);

                    HashMap<String, String> userMap = new HashMap<>();

                    userMap.put("name", name);
                    userMap.put("email" , email);
                    userMap.put("password" , password);
                    userMap.put("phone" , phone);
                    userMap.put("id" , id);
                    userMap.put("image", "default");
                    userMap.put("thumb_image", "default");


                    //////////new ////////

                    userMap.put("course1", "default");
                    userMap.put("course2", "default");
                    userMap.put("course3", "default");
                    userMap.put("mark", "default");
                    //////////new stop ////////




                    FirebaseDatabase.getInstance().getReference("Teachers").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();

                                Toast.makeText(getApplicationContext(), " Teacher signup successflly", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent i = new Intent(getApplicationContext(), TeacherHome.class);
                                startActivity(i);

                            } else {
                                progressDialog.dismiss();

                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(getApplicationContext(), " You are already signin ", Toast.LENGTH_SHORT).show();

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), " Please try again", Toast.LENGTH_SHORT).show();

                                }

                            }
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "an error accoured , Please try again", Toast.LENGTH_SHORT).show();

                }
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
