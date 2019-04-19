package com.minorproject.pariksha;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.minorproject.pariksha.Main2Activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

   Main2Activity obj;

    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mloginbtn;

   // private RadioGroup radiogroup;
    //private RadioButton radiobtn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Toolbar mToolbar;
    private EditText mkey;

    private ProgressDialog mloginProgress;

    private DatabaseReference mUserDatabase;

  /*  @Override
    public void onStateNotSaved() {
        super.onStateNotSaved();
        mAuth.addAuthStateListener(mAuthListener);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = (TextInputLayout) findViewById(R.id.login_email);
        mPassword = (TextInputLayout) findViewById(R.id.login_password);
        mloginbtn = (Button) findViewById(R.id.login_btn);

        mkey = (EditText) findViewById(R.id.login_key);

        mToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Log in");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(0xffffffff);

        mloginProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");


        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getEditText().getText().toString().trim();
                String password = mPassword.getEditText().getText().toString().trim();
                String key = mkey.getText().toString();
               // String key = Main2Activity.mkey.getText().toString();
                //Toast.makeText(LoginActivity.this,email,Toast.LENGTH_LONG).show();

               if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

                   mloginProgress.setTitle("Logging In");
                   mloginProgress.setMessage("Please wait while we are checking your credentials!");
                   mloginProgress.setCanceledOnTouchOutside(false);
                   mloginProgress.show();

                   loginUser(email, password,key);
               }
            }
        });

    }


    private void loginUser(String email, String password, final String key) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    //mloginProgress.dismiss();
                    //Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();

                    mUserDatabase.child(key).child("radiobtn").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String val = dataSnapshot.getValue().toString();
                            if(val.equals("Teacher")){
                                Intent i = new Intent(LoginActivity.this, TeacherActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                Intent i = new Intent(LoginActivity.this, StudentActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }

                       @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            mloginProgress.dismiss();
                            Toast.makeText(LoginActivity.this, "Cancel Request", Toast.LENGTH_SHORT).show();
                        }
                    });

                  //  final String current_user_id = mAuth.getCurrentUser().getUid();
                }else{
                    mloginProgress.dismiss();
                    Toast.makeText(LoginActivity.this, "Sign in Problem", Toast.LENGTH_SHORT).show();
                }
            }
        });
    };
}

