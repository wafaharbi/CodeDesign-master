package com.example.wafa.studentapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParentProfile extends AppCompatActivity {


    //databaseReference
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    //Activity Layout
    CircleImageView mDisplayImage;
    Button mNameBtn, mImageBtn , update;
    ListView listView;

    //for profile image
    private static final int GALLERY_PICK = 1;

    //Firbase Storage
    private StorageReference mImageStorage;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile);
//Arrow
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mDisplayImage = (CircleImageView) findViewById(R.id.settings_image);

        update = (Button) findViewById(R.id.update);
        listView = (ListView) findViewById(R.id.listInfoStudent);

        //Change Image
        mImageBtn = (Button) findViewById(R.id.settings_image_btn);
       // Image Storage Firebse
        mImageStorage = FirebaseStorage.getInstance().getReference();
        //Current User on Firebase
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        //Parent Table
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Parents").child(current_uid);
        mUserDatabase.keepSynced(true);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

          //For Change Profile Image

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


                mProgressDialog=new ProgressDialog(ParentProfile.this);
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
                                        Toast.makeText(ParentProfile.this, "Uploading  Successful", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(ParentProfile.this, "Error", Toast.LENGTH_LONG).show();
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
            final String email = info.setEmail(dataSnapshot.child("email").getValue().toString());
            final String password = info.setPassword(dataSnapshot.child("password").getValue().toString());
            final String phone = info.setPhone(dataSnapshot.child("phone").getValue().toString());
            final String image = dataSnapshot.child("image").getValue().toString();

            Picasso.with(ParentProfile.this).load(image).placeholder(R.drawable.default_img).into(mDisplayImage);


            ArrayList<String> array = new ArrayList<>();
            array.add(name);
            array.add(username);
            array.add(email);
            array.add(password);
            array.add(phone);

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);

            listView.setAdapter(arrayAdapter);


            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    Intent i = new Intent(getApplicationContext(), ParentUpadate.class);

                    i.putExtra("name", info.getName());
                    i.putExtra("email", info.getEmail());
                    i.putExtra("phone", info.getPhone());
                    i.putExtra("username", info.getUsername());

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


