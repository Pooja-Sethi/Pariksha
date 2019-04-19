package com.minorproject.pariksha;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class ProfileActivity extends AppCompatActivity {

    ImageView mpicture;
    ImageView mcamera;
    private TextInputEditText mUsername;
    String pathToFile;
    private static final int CAMERA_REQUEST_CODE = 1;   // this number is used to identify our image request
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private ProgressDialog mProgress;
    private ProgressBar mProgressBar;
    private Uri mImageUri;
    private TextView mDisplayName;
    private Toolbar mToolbar;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mpicture = (ImageView) findViewById(R.id.circular_profile);
        mcamera = (ImageView) findViewById(R.id.camera_img);
        mStorageRef = FirebaseStorage.getInstance().getReference("Uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Uploads");
        mDisplayName = (TextView) findViewById(R.id.display_name);


     //   mDisplayName.setText((CharSequence) Main2Activity.mUsername.getEditText().getText());

        mProgress = new ProgressDialog(this);
        mToolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(0xffffffff);

      /*  if(Build.VERSION.SDK_INT >= 23){
            requestPermissions(new String[]{ Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }*/
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
                    i.putExtra("res_id", R.drawable.profile);
                    startActivity(i);
                    finish();
                }
            }
        });

    }

 @Override
    protected void onRestart() {
     super.onRestart();


     Bundle bundle = getIntent().getExtras();
     if(bundle!=null && bundle.containsKey("full")) {
         uri = Uri.parse(bundle.getString("full"));
         mpicture.setImageURI(uri);
     }

    // Toast.makeText(ProfileActivity.this, "onRestart method called", Toast.LENGTH_LONG).show();
 }

    @Override
    protected void onStart() {
        super.onStart();


        /*Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("full")) {
            uri = Uri.parse(bundle.getString("full"));
            mpicture.setImageURI(uri);
        }
*/
        mpicture.setImageURI(uri);

        //Toast.makeText(ProfileActivity.this, "onStart method called", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("full")) {
            uri = Uri.parse(bundle.getString("full"));
            mpicture.setImageURI(uri);
        }*/
        mpicture.setImageURI(uri);
       // Toast.makeText(ProfileActivity.this, "onResume method called", Toast.LENGTH_LONG).show();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null
                && data.getData() != null) {

            mProgress.setMessage("Uploading Image..");
            mProgress.show();

            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).fit().into(mpicture, new Callback(){
                @Override
                public void onSuccess() {
                    mProgress.dismiss();
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
            //if the user gets the image then it must be uploaded with unique name otherwise same name of files can overwrite
            // the unique name can be generated by taking current time in milliseconds because it will change fast that we never get same number twice
            StorageReference fileRef = mStorageRef.child(mDisplayName + "."+getFileExtension(mImageUri)); //generate name of file
            //it will create something like "Uploads/127773898.jpg"
            fileRef.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //mProgressBar.setProgress(0);<- if we write this then the progress bar disappears immediately and user can't even see it propery
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                        }
                    }, 500);
                    Toast.makeText(ProfileActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();
                    UploadPhoto uploadPhoto = new UploadPhoto(mDisplayName.toString(), mImageUri);
                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(uploadPhoto);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    //here we will update our progress bar
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());//to extract data from taskSnapshot
                    mProgressBar.setProgress((int)progress);
                }
            });

        }else{
            Toast.makeText(ProfileActivity.this, "no file selected", Toast.LENGTH_LONG).show();
        }
    }

}