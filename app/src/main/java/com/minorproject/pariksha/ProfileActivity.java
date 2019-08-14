package com.minorproject.pariksha;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.TooltipCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.minorproject.pariksha.Adpters.ProfileModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import static android.os.Environment.getExternalStoragePublicDirectory;
import static com.minorproject.pariksha.R.drawable.profile;

public class ProfileActivity extends AppCompatActivity {

    ImageView mpicture,mcamera;
    private TextInputEditText mUsername;
    String pathToFile;
    private static final int CAMERA_REQUEST_CODE = 1;   // this number is used to identify our image request
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef, mRef, registerRef;
    private ProgressDialog mProgress;
    private Uri mImageUri;
    private TextView mDisplayName;
    private Toolbar mToolbar;
    Uri uri;
    String downloadImageUrl,uploadId,pKey;
    public static EditText phoneNumber;
    public static String phnStr;
    Button submitPhonebtn;
    String image;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mpicture = (ImageView) findViewById(R.id.circular_profile);
        mcamera = (ImageView) findViewById(R.id.camera_img);
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("Uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Profile");
        mRef = FirebaseDatabase.getInstance().getReference().child("Profile");
        registerRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mDisplayName = (TextView) findViewById(R.id.display_name);

        pKey = LoginActivity.mkey.getText().toString();
        phoneNumber = (EditText) findViewById(R.id.phone_no);
        phnStr  =phoneNumber.getText().toString();

       //   mDisplayName.setText((CharSequence) Main2Activity.mUsername.getEditText().getText());

        mProgress = new ProgressDialog(this);
        mToolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(0xffffffff);

       // mpicture.setImageResource(R.drawable.profile);

        submitPhonebtn =(Button) findViewById(R.id.submit_phn);

        TooltipCompat.setTooltipText(mcamera, "take picture!");

        uploadId = Main2Activity.mkey.getEditableText().toString();

      /*  if(Build.VERSION.SDK_INT >= 23){
            requestPermissions(new String[]{ Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }*/
        registerRef.child(pKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String nameStr = dataSnapshot.child("name").getValue().toString();
                mDisplayName.setText(nameStr);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //to store image to database
       /* mDatabaseRef.child(pKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.child("image").getValue().toString().equals(null)) {
                    String image = dataSnapshot.child("image").getValue().toString();
                    Picasso.with(getApplicationContext()).load(image).into(mpicture);
                }
                else {
                    mpicture.setImageURI(mImageUri);
                    Picasso.with(getApplicationContext()).load(mImageUri.toString()).into(mpicture);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        mcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFileChooser();

               /* Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);*/

            }
        });

        mpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mImageUri.toString()!=null) {

                    Intent i = new Intent(ProfileActivity.this, FullImageActivity.class);
                    i.putExtra("res_id", mImageUri.toString());
                    startActivity(i);
                    finish();

                }else if(mImageUri.toString().equals(R.string.profile) || mImageUri.toString()==null){

                    Intent i =new Intent(ProfileActivity.this,FullImageActivity.class);
                    i.putExtra("res_id", profile);
                    startActivity(i);
                    finish();
                }
            }
        });

        submitPhonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phn= phoneNumber.getText().toString();
                image = mImageUri.toString();
                ProfileModel pm = new ProfileModel(pKey, phn,mDisplayName.getText().toString(), image);

                mRef.child(pKey).setValue(pm);
            }
        });
       // uploadFile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activities_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id){

            case R.id.give_test_menu:
                Intent i = new Intent(ProfileActivity.this, GiveExamActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.discussion_menu:
                    Intent intent= new Intent(ProfileActivity.this, DiscussionActivity.class);
                startActivity(intent);
                return  true;

            case R.id.logout_profile_menu:
                mAuth.signOut();
                finish();
                return true;

                default:
                 return super.onOptionsItemSelected(item);
        }
    }

   /* @Override
    protected void onRestart() {
     super.onRestart();


     Bundle bundle = getIntent().getExtras();
     if(bundle!=null && bundle.containsKey("full")) {
         uri = Uri.parse(bundle.getString("full"));
         mpicture.setImageURI(uri);
     }

    // Toast.makeText(ProfileActivity.this, "onRestart method called", Toast.LENGTH_LONG).show();
 }*/

   @Override
    protected void onStart() {
        super.onStart();

      /* mDatabaseRef.child(pKey).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               if(!dataSnapshot.child("image").getValue().toString().equals(null)) {
                   String image = dataSnapshot.child("image").getValue().toString();
                   Picasso.with(getApplicationContext()).load(image).into(mpicture);
               }
               else {
                   mpicture.setImageURI(mImageUri);
                   Picasso.with(getApplicationContext()).load(mImageUri.toString()).into(mpicture);
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
*/

       //Picasso.with(this).load(mImageUri).into(mpicture);
        /*Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("full")) {
            uri = Uri.parse(bundle.getString("full"));
            mpicture.setImageURI(uri);
        }
*/
        //mpicture.setImageURI(uri);

        //Toast.makeText(ProfileActivity.this, "onStart method called", Toast.LENGTH_LONG).show();
    }

    @Override
   protected void onResume() {
        super.onResume();

       /* mDatabaseRef.child(pKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.child("image").getValue().toString().equals(null)) {
                    String image = dataSnapshot.child("image").getValue().toString();
                    Picasso.with(getApplicationContext()).load(image).into(mpicture);
                }
                else {
                    mpicture.setImageURI(mImageUri);
                    Picasso.with(getApplicationContext()).load(mImageUri.toString()).into(mpicture);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/
      /* mcamera.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               openFileChooser();
           }
       });*/
       /* Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("full")) {
            uri = Uri.parse(bundle.getString("full"));
            mpicture.setImageURI(uri);
        }*/
      //  mpicture.setImageURI(uri);
       //Toast.makeText(ProfileActivity.this, "onResume method called", Toast.LENGTH_LONG).show();
    }

    //method to choose file from which we select image
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an Image"), CAMERA_REQUEST_CODE);
    }

    //the following method should bw called so that the user can select an image from the file
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null
                && data.getData() != null) {

            mProgress.setMessage("Uploading Image..");
            mProgress.show();

            mImageUri = data.getData();

             image = mImageUri.toString();

            //mDatabaseRef.child(pKey).child("profile").child()setValue(mImageUri.toString());

            Picasso.with(this).load(mImageUri).fit().into(mpicture, new Callback(){
                @Override
                public void onSuccess() {
                    mProgress.dismiss();
                    uploadFile();
                    //now we have to upload the image
                    //mDatabaseRef.child(pKey).child("profile").setValue(mImageUri.toString());
                }

                @Override
                public void onError() {
                    mProgress.dismiss();
                     Toast.makeText(ProfileActivity.this, "Error in loading image", Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    //to get file extension from the selected image
    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }


    public void uploadFile(){
        if (mImageUri!=null){

            final ProgressDialog mProgressBar = new ProgressDialog(this);
            mProgressBar.setMessage("Uploading..");
            //if the user gets the image then it must be uploaded with unique name otherwise same name of files can overwrite
            // the unique name can be generated by taking current time in milliseconds because it will change fast that we never get same number twice
            final StorageReference fileRef = mStorageRef.child(System.currentTimeMillis() + "."+getFileExtension(mImageUri)); //generate name of file
            //it will create something like "Uploads/127773898.jpg"
            
            //dtoring imag to database
            /*mDatabaseRef.child(pKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String image = dataSnapshot.child("image").getValue().toString();
                    Uri imageUri = Uri.parse(image);
                    Picasso.with(getApplicationContext()).load(imageUri).into(mpicture);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });*/
           /* final UploadTask uploadTask = fileRef.putFile(mImageUri);


            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    String message = e.toString();
                    Toast.makeText(ProfileActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(ProfileActivity.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {


                            if(!task.isSuccessful()){
                                throw  task.getException();
                            }

                            downloadImageUrl = fileRef.getDownloadUrl().toString();
                            return fileRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {

                            if (task.isSuccessful()){

                                downloadImageUrl = task.getResult().toString();

                                Toast.makeText(ProfileActivity.this, "Successfully get image url", Toast.LENGTH_SHORT).show();
                                saveImageInfoToDatabase();
                            }
                        }
                    });
                }
            });*/
           fileRef.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //mProgressBar.setProgress(0);<- if we write this then the progress bar disappears immediately and user can't even see it propery
                   /* Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //mProgressBar.setProgress(0);
                        }
                    }, 500);*/
                   mProgressBar.dismiss();
                    Toast.makeText(ProfileActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();

                   // UploadPhoto uploadPhoto = new UploadPhoto(mDisplayName.toString(), taskSnapshot.getDownloadUrl().toString());

                   /* UploadPhoto uploadPhoto = new UploadPhoto(mDisplayName.toString(), taskSnapshot.getUploadSessionUri());
                    String uploadId = Main2Activity.mkey.getEditableText().toString();
                    mDatabaseRef.child(uploadId).setValue(uploadPhoto);*/

                   // String uploadId = mDatabaseRef.push().getKey();
                    //mDatabaseRef.child(uploadId).setValue(uploadPhoto);

                }
            })
           .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgressBar.dismiss();
                    Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    //here we will update our progress bar
                  double progress = (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();//to extract data from taskSnapshot
                    mProgressBar.setProgress((int)progress);
                }
            });
        }else{
            Toast.makeText(ProfileActivity.this, "no file selected", Toast.LENGTH_LONG).show();
        }
    }

    private void saveImageInfoToDatabase() {

        HashMap<String, Object> imageMap = new HashMap<>();
        imageMap.put("image id", uploadId);
        imageMap.put("image url", downloadImageUrl);

        mDatabaseRef.child(uploadId).updateChildren(imageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(ProfileActivity.this, "Image is added to database successfully", Toast.LENGTH_SHORT).show();
                }else {

                    String message = task.getException().toString();
                    Toast.makeText(ProfileActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}