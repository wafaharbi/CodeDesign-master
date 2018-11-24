package com.example.wafa.studentapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
//import id.zelory.compressor.Compressor;

public class StudentProfile extends AppCompatActivity {


    private DatabaseReference mUserDatabase;   //databaseReference

    private FirebaseUser mCurrentUser;
//Android Layout

    CircleImageView mDisplayImage;
    Button mNameBtn, mImageBtn , update;
    ListView listView;


    private static final int GALLERY_PICK = 1;
    //Firbase Storage
    private StorageReference mImageStorage;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

//Arrow
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mDisplayImage = (CircleImageView) findViewById(R.id.settings_image);

        update = (Button) findViewById(R.id.update);
        listView = (ListView) findViewById(R.id.listInfoStudent);




       // mNameBtn = (Button) findViewById(R.id.settings_name_btn);         //Change name
        mImageBtn = (Button) findViewById(R.id.settings_image_btn);        //Change Image

        mImageStorage = FirebaseStorage.getInstance().getReference();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();     //u


        //Student
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Student").child(current_uid);
        mUserDatabase.keepSynced(true);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                showData(dataSnapshot);

                //  Toast.makeText(SettingsActivity.this,dataSnapshot.toString(),Toast.LENGTH_LONG).show();
                /*
                String name = dataSnapshot.child("name").getValue().toString();
                String username = dataSnapshot.child("username").getValue().toString();
                String password = dataSnapshot.child("password").getValue().toString();

//wafaaaaaaaaaa cooode

                String image = dataSnapshot.child("image").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();


                String phone = dataSnapshot.child("phone").getValue().toString();

                mName.setText(name);
                mUsername.setText(username);
                mPass.setText(password);
                mPhone.setText(phone);
                */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // not important nooooooo

       /* mNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name_value = mName.getText().toString();

                Intent name_intent = new Intent(StudentProfile.this, UpdateProfile.class);

                name_intent.putExtra("name_value", name_value);
                startActivity(name_intent);

            }
        });
*/
////////////////////////////////For Image ////////////////////////////

        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);



               /* CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(StudentProfile.this);
*/

            }
        });


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {


            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .setMinCropWindowSize(500, 500)
                    .start(this);


            // Toast.makeText(StudentProfile.this, imageUri, Toast.LENGTH_LONG).show();

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {


                mProgressDialog=new ProgressDialog(StudentProfile.this);
                mProgressDialog.setTitle("Uploading Image..");
                mProgressDialog.setMessage("Please wait while we upload and process the image.");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();
                Uri resultUri = result.getUri();

                String current_user_id = mCurrentUser.getUid();

                StorageReference filepath = mImageStorage.child("Profile_images").child(current_user_id+".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            // Toast.makeText(StudentProfile.this, "Work", Toast.LENGTH_LONG).show();

                            //  final String download_url = task.getResult().getDownloadUrl().toString();

                            String download_url = task.getResult().getDownloadUrl().toString();

                            mUserDatabase.child("image").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        mProgressDialog.dismiss();
                                        Toast.makeText(StudentProfile.this, "Uploading  Successful", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(StudentProfile.this, "Error", Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();
                        }


                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }}}

    public void showData(DataSnapshot dataSnapshot) {

        final User info = new User();


        for (DataSnapshot ds : dataSnapshot.getChildren()) {


            final String name = info.setName(dataSnapshot.child("name").getValue().toString());
            final String username = info.setUsername(dataSnapshot.child("username").getValue().toString());
            //final String email = info.setEmail(dataSnapshot.child("email").getValue().toString());
            final String password = info.setPassword(dataSnapshot.child("password").getValue().toString());
            final String phone = info.setPhone(dataSnapshot.child("phone").getValue().toString());
            final String image = dataSnapshot.child("image").getValue().toString();

            final String course1 = info.setCourse1(dataSnapshot.child("course1").getValue().toString());
            final String mark = info.setMark(dataSnapshot.child("mark").getValue().toString());



            Picasso.with(StudentProfile.this).load(image).placeholder(R.drawable.default_img).into(mDisplayImage);


            ArrayList<String> array = new ArrayList<>();
            array.add(name);
            array.add(username);
           // array.add(email);
            array.add(password);
            array.add(phone);

            array.add(course1);
            array.add(mark);

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);

            listView.setAdapter(arrayAdapter);


            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    Intent i = new Intent(getApplicationContext(), StudentUpdateInfo.class);

                    i.putExtra("name", info.getName());
                  //  i.putExtra("email", info.getEmail());
                    i.putExtra("phone", info.getPhone());
                    i.putExtra("username", info.getUsername());


                    i.putExtra("course1", info.getCourse1());
                    i.putExtra("mark", info.getMark());

                    startActivity(i);
                }
            });




        }
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(20);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }


}

