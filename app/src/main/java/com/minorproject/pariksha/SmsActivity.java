package com.minorproject.pariksha;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minorproject.pariksha.Adpters.ProfileModel;

import org.w3c.dom.Text;

public class SmsActivity extends AppCompatActivity {

    Button smsbtn;
    TextView  smsText, topicText, sms_lineText;
    EditText phoneText;
    String phoneNo,final_message ;
    String rkey;

    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        smsbtn = (Button) findViewById(R.id.smsbtn);
        phoneText = (EditText) findViewById(R.id.phn_no_text);
        smsText = (TextView) findViewById(R.id.sms_text);
        sms_lineText = (TextView)findViewById(R.id.sms_lineText);
        topicText = (TextView) findViewById(R.id.topic_name);
        topicText.setText(GiveExamActivity.str1); //Topic name

        mRef = FirebaseDatabase.getInstance().getReference("Profile");
        rkey = LoginActivity.mkey.getText().toString();

        mRef.child(rkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ProfileModel pm = dataSnapshot.getValue(ProfileModel.class);
                phoneNo = dataSnapshot.child("phone_no").getValue().toString();
                phoneText.setText(phoneNo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Intent  i = getIntent();
        Bundle bundle = i.getExtras();
        if(bundle!=null && bundle.containsKey("marks")) {

            String str = bundle.getString("marks");
            smsText.setText(str);
        }

        final_message = sms_lineText.getText().toString()+" "+topicText.getText().toString()+" are: "+smsText.getText().toString();
        smsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int permissionCheck = ContextCompat.checkSelfPermission(SmsActivity.this, android.Manifest.permission.SEND_SMS);

                //if the following condition is true then this means that user allows us to use the resources
                if (permissionCheck == PackageManager.PERMISSION_GRANTED){
                    MyMessage();

                }else{
                    ActivityCompat.requestPermissions(SmsActivity.this, new String[]{Manifest.permission.SEND_SMS}, 0);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case 0:

                if (grantResults.length>=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    MyMessage();

                }else {
                    Toast.makeText(this, "You don't have required permission to make action", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void MyMessage() {

        /*phoneText.setText(phoneNo);
        Intent  i = getIntent();
        smsText .setText(i.getStringExtra("marks"));*/

        if (!phoneText.getText().toString().equals("") || !smsText.getText().toString().equals("")) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneText.getText().toString(), null, final_message, null, null);
            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "Message not  sent", Toast.LENGTH_SHORT).show();

        }

        Intent i = new Intent(SmsActivity.this, StudentActivity.class);
        startActivity(i);
        finish();
    }
}
