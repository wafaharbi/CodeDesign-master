package com.example.wafa.studentapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProfile extends AppCompatActivity {
    private Toolbar mToolbar;

    private TextInputLayout mName;  //mStatus
    private TextInputLayout mEmail;
    private TextInputLayout mPhone;


    private Button mSavebtn;


    //Firebase
    private DatabaseReference mStatusDatabase;
    private FirebaseUser mCurrentUser;


    //Progress
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        //Firebase
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        mStatusDatabase = FirebaseDatabase.getInstance().getReference().child("Student").child(current_uid);

        mToolbar = (Toolbar) findViewById(R.id.status_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Account Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String name_value = getIntent().getStringExtra("name_value");

        mName = (TextInputLayout) findViewById(R.id.updateName);
        mSavebtn = (Button) findViewById(R.id.status_save_btn);

        mName.getEditText().setText(name_value);
        /*mEmail = (TextInputLayout) findViewById(R.id.updateEmail);
        mPhone = (TextInputLayout) findViewById(R.id.updatePhone);
*/



        // mEmail.getEditText().setText(status_value);

        //mPhone.getEditText().setText(status_value);



        mSavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Progress
                mProgress = new ProgressDialog(UpdateProfile.this );
                mProgress.setTitle("Saving Changes");
                mProgress.setMessage("Please wait while we save the changes");
                mProgress.show();


                String names = mName.getEditText().getText().toString();

                mStatusDatabase.child("name").setValue(names).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            mProgress.dismiss();

                        } else {

                            Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                        }

                    }
                });

            }
        });



    }
}



