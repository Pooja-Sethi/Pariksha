package com.minorproject.pariksha;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ResultActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    TextView t1,t2,t3, result_name, result_class;
    Button resultBtn;
    int marks;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    String resultKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mAuth = FirebaseAuth.getInstance();

        mRef = FirebaseDatabase.getInstance().getReference().child("Users");
        resultKey = LoginActivity.mkey.getText().toString();

        t1=(TextView) findViewById(R.id.textview1);
        t2=(TextView) findViewById(R.id.textView2);
        t3=(TextView) findViewById(R.id.textView3);

        result_name = (TextView) findViewById(R.id.result_name);
        result_class = (TextView) findViewById(R.id.result_class_text);

        result_name.setText(Main2Activity.nameStr);

        result_class.setText(GiveExamActivity.text1);

        resultBtn = (Button) findViewById(R.id.result_btn);

        mRef.child(resultKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                result_name.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       /* Toast.makeText(this, "Name: "+result_name, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Class: "+result_class, Toast.LENGTH_SHORT).show();
        */

        mToolbar = (Toolbar) findViewById(R.id.result_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(0xffffffff);

        Intent i =getIntent();

        String questions = i.getStringExtra("total");
        String correct = i.getStringExtra("correct");
        String wrong = i.getStringExtra("incorrect");

        t1.setText(questions);
        t2.setText(correct);
        t3.setText(wrong);
       // marks = Integer.parseInt(correct);

        final String marks = correct;
        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ResultActivity.this, SmsActivity.class);
                i.putExtra("marks",marks);
                startActivity(i);
                finish();
            }
        });

    }
}
