package com.minorproject.pariksha;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class StartTestActivity extends AppCompatActivity {

    private Button b1, b2,b3,b4;
    private TextView quesText, qnum;
    private Toolbar mToolbar;

    int total=0;
    int correct = 0;
    int wrong = 0;

    DatabaseReference reference;

    TextView timeText;
    private  String mtopic;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        mtopic =  GiveExamActivity.str1;

        b1=(Button) findViewById(R.id.btn_1);
        b2=(Button) findViewById(R.id.btn_2);
        b3=(Button) findViewById(R.id.btn_3);
        b4=(Button) findViewById(R.id.btn_4);
        quesText = (TextView) findViewById(R.id.ques_text_stu);
        qnum = (TextView) findViewById(R.id.q_no);

        timeText = (TextView) findViewById(R.id.time_text);

        mToolbar = (Toolbar) findViewById(R.id.start_test_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(mtopic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(0xffffffff);

        updateQuestion();
        reverseTimer(30, timeText);
    }

    private void updateQuestion() {

         total++;
         if(total > 5){
             //open result activity

             Intent i =new Intent(StartTestActivity.this, ResultActivity.class);
             i.putExtra("total", String.valueOf(total));
             i.putExtra("correct", String.valueOf(correct));
             i.putExtra("incorrect", String.valueOf(wrong));
             startActivity(i);

         }else {

             reference = FirebaseDatabase.getInstance().getReference().child(GiveExamActivity.text1).child(mtopic).child(String.valueOf(total));
             reference.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                     //ques is accessed from inner class therefore declare final
                     final Question ques = dataSnapshot.getValue(Question.class);
                     qnum.setText(ques.getQnum());
                     quesText.setText(ques.getQuestion());
                     b1.setText(ques.getOption1());
                     b2.setText(ques.getOption2());
                     b3.setText(ques.getOption3());
                     b4.setText(ques.getOption4());

                     Toast.makeText(StartTestActivity.this, "value: "+dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                     //button 1 clicked
                     b1.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {

                             if (b1.getText().toString().equals(reference.child("correct_answer"))){

                                 b1.setBackgroundColor(Color.parseColor("#008000"));
                                 Toast.makeText(StartTestActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                                 Handler handler = new Handler();
                                 handler.postDelayed(new Runnable() {
                                     @Override
                                     public void run() {
                                         correct++;
                                         b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         updateQuestion();
                                     }
                                 }, 1500);
                             }else {
                                 //if answer is wrong

                                 wrong++;
                                 b1.setBackgroundColor(Color.RED);
                                 Toast.makeText(StartTestActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();

                                 if(b2.getText().toString().equals(ques.getCorrect_answer())){
                                     b2.setBackgroundColor(Color.GREEN);

                                 }else if (b3.getText().toString().equals(ques.getCorrect_answer())){
                                     b3.setBackgroundColor(Color.GREEN);

                                 }else if (b4.getText().toString().equals(ques.getCorrect_answer())){
                                     b4.setBackgroundColor(Color.GREEN);

                                 }

                                 Handler handler = new Handler();
                                 handler.postDelayed(new Runnable() {
                                     @Override
                                     public void run() {
                                         b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         updateQuestion();
                                     }
                                 },1500);
                             }
                         }
                     });

                     //button 2 clicked
                     b2.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             if (b2.getText().toString().equals(ques.getCorrect_answer())){

                                 b2.setBackgroundColor(Color.parseColor("#008000"));

                                 Toast.makeText(StartTestActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                                 Handler handler = new Handler();
                                 handler.postDelayed(new Runnable() {
                                     @Override
                                     public void run() {
                                         correct++;
                                         b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         updateQuestion();
                                     }
                                 }, 1500);
                             }else {
                                 //if answer is wrong

                                 wrong++;
                                 b2.setBackgroundColor(Color.RED);
                                 Toast.makeText(StartTestActivity.this, "Inorrect!", Toast.LENGTH_SHORT).show();
                                 if(b1.getText().toString().equals(ques.getCorrect_answer())){
                                     b1.setBackgroundColor(Color.GREEN);
                                 }else if (b3.getText().toString().equals(ques.getCorrect_answer())){
                                     b3.setBackgroundColor(Color.GREEN);
                                 }else if (b4.getText().toString().equals(ques.getCorrect_answer())){
                                     b4.setBackgroundColor(Color.GREEN);
                                 }

                                 Handler handler = new Handler();
                                 handler.postDelayed(new Runnable() {
                                     @Override
                                     public void run() {
                                         b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         updateQuestion();
                                     }
                                 },1500);
                             }
                         }

                     });

                     b3.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             if (b3.getText().toString().equals(ques.getCorrect_answer())){

                                 b3.setBackgroundColor(Color.parseColor("#008000"));
                                 Toast.makeText(StartTestActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                                 Handler handler = new Handler();
                                 handler.postDelayed(new Runnable() {
                                     @Override
                                     public void run() {
                                         correct++;
                                         b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         updateQuestion();
                                     }
                                 }, 1500);
                             }else {
                                 //if answer is wrong

                                 wrong++;
                                 b3.setBackgroundColor(Color.RED);
                                 Toast.makeText(StartTestActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                                 if(b1.getText().toString().equals(ques.getCorrect_answer())){
                                     b1.setBackgroundColor(Color.GREEN);
                                 }else if (b2.getText().toString().equals(ques.getCorrect_answer())){
                                     b2.setBackgroundColor(Color.GREEN);
                                 }else if (b4.getText().toString().equals(ques.getCorrect_answer())){
                                     b4.setBackgroundColor(Color.GREEN);
                                 }

                                 Handler handler = new Handler();
                                 handler.postDelayed(new Runnable() {
                                     @Override
                                     public void run() {
                                         b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         updateQuestion();
                                     }
                                 },1500);
                             }
                         }

                     });

                     b4.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {

                             if (b4.getText().toString().equals(ques.getCorrect_answer())){

                                 b4.setBackgroundColor(Color.parseColor("#008000"));
                                 correct++;
                                 Toast.makeText(StartTestActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                                 Handler handler = new Handler();
                                 handler.postDelayed(new Runnable() {
                                     @Override
                                     public void run() {
                                         b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         updateQuestion();
                                     }
                                 }, 1500);
                             }else {
                                 //if answer is wrong

                                 wrong++;
                                 b4.setBackgroundColor(Color.RED);
                                 Toast.makeText(StartTestActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                                 if(b1.getText().toString().equals(ques.getCorrect_answer())){
                                     b1.setBackgroundColor(Color.GREEN);
                                 }else if (b2.getText().toString().equals(ques.getCorrect_answer())){
                                     b2.setBackgroundColor(Color.GREEN);
                                 }else if (b3.getText().toString().equals(ques.getCorrect_answer())){
                                     b3.setBackgroundColor(Color.GREEN);
                                 }

                                 Handler handler = new Handler();
                                 handler.postDelayed(new Runnable() {
                                     @Override
                                     public void run() {
                                         b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                         updateQuestion();
                                     }
                                 },1500);
                             }
                         }
                     });

                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });
         }
}

    public void reverseTimer(int seconds, final TextView tv){

         //anonymous class countDownTimer
         new CountDownTimer(seconds*1000+ 1000, 1000){

             @Override
             public void onTick(long millisUntilFinished) {
                 int seconds = (int)(millisUntilFinished/1000);
                 int minutes=seconds/60;
                 seconds=seconds%60;
                 tv.setText(String.format(String.format("%02d", minutes) + ":" + String.format("%02d", seconds)));

             }

             @Override
             public void onFinish() {

                 tv.setText("Completed");
                 Intent i = new Intent(StartTestActivity.this, ResultActivity.class);
                 i.putExtra("total", String.valueOf(total));
                 i.putExtra("correct", String.valueOf(correct));
                 i.putExtra("incorrect", String.valueOf(wrong));
                 startActivity(i);
             }
         }.start();
    }
}
