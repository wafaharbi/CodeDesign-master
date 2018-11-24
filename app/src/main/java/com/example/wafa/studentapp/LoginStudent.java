package com.example.wafa.studentapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginStudent extends AppCompatActivity {

ImageView signup;
    TextView forget;
    EditText studentEmail, studentPassword , studentUsername;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);

    //    studentUsername = (EditText) findViewById( R.id.editUsername);
        studentEmail = (EditText) findViewById(R.id.editElogin);
        studentPassword = (EditText) findViewById(R.id.editPlogin);
        forget = (TextView) findViewById(R.id.textviewForget);
        signup = (ImageView) findViewById(R.id.textViewSignup);
        auth = FirebaseAuth.getInstance();

      ref = FirebaseDatabase.getInstance().getReference().child("Student");






        signup.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent i =new Intent( getApplicationContext() , SignupStudent.class);
                                          startActivity(i);
                                      }
                                  });

                forget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), ForgetPassword.class);
                        startActivity(i);
                    }
                });

    }


    public void userloginStudent(View v) {

       // final String username = studentUsername.getText().toString().trim();
        final String email = studentEmail.getText().toString().trim();
        final String password = studentPassword.getText().toString().trim();



       /* if (username.isEmpty()) {
            studentUsername.setError(" invalid username");
            studentUsername.requestFocus();
            return;

        }
*/

        // Checking the email id and password is Empty
        if (email.isEmpty()) {
            studentEmail.setError("Please enter your email");      /*if mail address is NULL string*/
            studentEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            studentEmail.setError("Invalid email");               // if mail address is NOT contain(@ or .)
            studentEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            studentPassword.setError("Please enter your password");    /*if password is NULL string*/
            studentPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {                                    //  if password length < 6
            studentPassword.setError("password at least 6 numbers");
            studentPassword.requestFocus();
            return;
        }

/**

    ref.child(username).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            User user = dataSnapshot.getValue(User.class);

            Log.i("I am here ", " ###############");

            if (username.equals(user.getUsername())) {


                Toast.makeText(getApplicationContext(), " correct student ID", Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
**/
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in, please wait...");
        progressDialog.show();

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override

                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {

                        Toast.makeText(getApplicationContext(), " user login ", Toast.LENGTH_SHORT).show();
                        finish();
                      Intent i = new Intent(getApplicationContext(), StudentHome.class);
                        startActivity(i);
                        //startActivity(new Intent(LoginStudent.this, users.class));

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "you should  Signup first ", Toast.LENGTH_SHORT).show();

                    }
                }
            });


    }


}
