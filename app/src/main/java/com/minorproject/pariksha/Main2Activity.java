package com.minorproject.pariksha;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup;
import android.widget.RadioButton;

import com.firebase.client.Firebase;
import com.google.android.gms.internal.firebase_auth.zzao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    public static TextInputLayout mUsername;
    private Button mregbtn;
    private Button mloginbtn;
    public static RadioGroup radiogroup;
    public static RadioButton radiobtn;

    public static String rbutton;
    private FirebaseAuth mAuth;

    public static EditText mkey;
    private DatabaseReference mDatabase;
    public static String nameStr;

    private Toolbar mtoolbar;

    private ProgressDialog mRegProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //Firebase.setAndroidContext(this);

        mAuth = FirebaseAuth.getInstance();
        mEmail = (TextInputLayout) findViewById(R.id.emailInputLayout);
        mPassword = (TextInputLayout) findViewById(R.id.passwordText);
        mUsername = (TextInputLayout) findViewById(R.id.username);
        mregbtn = (Button) findViewById(R.id.sign_up_btn);
        //textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        mloginbtn = (Button) findViewById(R.id.loginbtn);
        radiogroup = (RadioGroup) findViewById(R.id.options);

        mkey = (EditText) findViewById(R.id.key);
        nameStr = mUsername.getEditText().getText().toString();

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
       // mRef = new Firebase("https://pariksha-bdd26.firebaseio.com/");

        //textViewSignin.setOnClickListener((View.OnClickListener) this);

        mAuth = FirebaseAuth.getInstance();

        mtoolbar = (Toolbar) findViewById(R.id.reg_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("SignUp");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtoolbar.setTitleTextColor(0xffffffff);

        mRegProgress = new ProgressDialog(this);

        mregbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mUsername.getEditText().getText().toString().trim();
                String email = mEmail.getEditText().getText().toString().trim();
                String password = mPassword.getEditText().getText().toString().trim();
                String skey = mkey.getText().toString();

                if(validateEmail() || validateUsername() || validatePassword()) {

                    mRegProgress.setTitle("Registering User");
                    mRegProgress.setMessage("Please wait for few minutes");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();


                    register_user(name, email, password,skey);
                }
            }
        });


     mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


    }

    /*public void onClick(View view){
        if(view == textViewSignin){
            startActivity(new Intent(Main2Activity.this, LoginActivity.class));
        }
    }*/

    //validating email

    private boolean validateEmail() {
        String emailInput = mEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            mEmail.setError("Field can't be empty");
            return false;

        } else {

            mEmail.setError(null);
            return true;

        }
    }


    //validating username
    private boolean validateUsername() {

        String username = mUsername.getEditText().getText().toString().trim();

        if (username.isEmpty()) {
            mUsername.setError("Field can't be empty");
            return false;

        } else if (username.length() > 15) {
            mUsername.setError("Username too long!");
            return false;

        } else {
            mUsername.setError(null);
            return true;
        }
    }

    //validating password
    private boolean validatePassword() {

        String passwordInput = mPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            mPassword.setError("Field can't be empty");
            return false;
        } else {

            mPassword.setError(null);
            return true;
        }
    }



    private void register_user(final String name, final String email, final String password, final String skey) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Main2Activity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // Toast.makeText(Main2Activity.this, "Registering user", Toast.LENGTH_LONG).show();
                mRegProgress.dismiss();

                if (!task.isSuccessful()) {
                    Toast.makeText(Main2Activity.this, "Authentication Failed", Toast.LENGTH_LONG).show();

                } else {
                    int selectedId = radiogroup.getCheckedRadioButtonId();
                    radiobtn = findViewById(selectedId);
                    rbutton = radiobtn.getText().toString();

                   String id = mDatabase.push().getKey();

                    addInformation ai = new addInformation(id, name, email, password, rbutton);
                   // mDatabase.child(id).setValue(ai);
                    Toast.makeText(Main2Activity.this, "values: "+mDatabase, Toast.LENGTH_LONG).show();
                   // Toast.makeText(Main2Activity.this, "added Information", Toast.LENGTH_LONG).show();

                    if (rbutton.equals("Teacher")) {
                        mDatabase.child(skey).setValue(ai);
                        Intent i = new Intent(Main2Activity.this, TeacherActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        mDatabase.child(skey).setValue(ai);
                        Intent i = new Intent(Main2Activity.this, StudentActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });
    }


}
