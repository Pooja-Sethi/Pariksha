package com.minorproject.pariksha;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentActivity extends AppCompatActivity {

   /* TextView t1_question;
    Button b1,b2,b3,b4;

    int total =1;
    int correct = 0, wrong=0;

    DatabaseReference dref;*/

   ImageView giveTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        giveTest = (ImageView) findViewById(R.id.give_exam_img);

        giveTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(StudentActivity.this, GiveExamActivity.class);
                startActivity(i);
                finish();
            }
        });

        /*t1_question = (TextView) findViewById(R.id.question);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);

        updateQuestion();*/
    }

  /*  private void updateQuestion() {

        if(total > 5){
            //open the result activity.
        }else{

            dref = FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(total));
            dref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final Question question = dataSnapshot.getValue(Question.class); //object of Question class

                    //set questions and options.
                    t1_question.setText(question.getQuestion());
                    b1.setText(question.getOption1());
                    b2.setText(question.getOption2());
                    b3.setText(question.getOption3());
                    b4.setText(question.getOption4());


                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(b1.getText().toString().equals(question.getAnswer())){

                                b1.setBackgroundColor(Color.GREEN);

                                Handler handler = new Handler(); // because we want delay to show right answer with green and wrong answer with red
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        correct++;
                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));

                                        updateQuestion();

                                    }
                                }, 1000);
                            }else {

                                //answer is wrong  then here we will find the correct answer and make it green.

                                wrong++;
                                b1.setBackgroundColor(Color.RED);

                                if(b2.getText().toString().equals(question.getAnswer())){
                                    b2.setBackgroundColor(Color.GREEN);
                                }
                                else if(b3.getText().toString().equals(question.getAnswer())){
                                    b3.setBackgroundColor(Color.GREEN);
                                }else{
                                    b4.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler =new Handler();
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


                    b2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(b2.getText().toString().equals(question.getAnswer())){

                                b2.setBackgroundColor(Color.GREEN);

                                Handler handler = new Handler(); // because we want delay to show right answer with green and wrong answer with red
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        correct++;
                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));

                                        updateQuestion();

                                    }
                                }, 1000);
                            }else {

                                //answer is wrong  then here we will find the correct answer and make it green.

                                wrong++;
                                b2.setBackgroundColor(Color.RED);

                                if(b1.getText().toString().equals(question.getAnswer())){
                                    b1.setBackgroundColor(Color.GREEN);
                                }
                                else if(b3.getText().toString().equals(question.getAnswer())){
                                    b3.setBackgroundColor(Color.GREEN);
                                }else{
                                    b4.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler =new Handler();
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
                            if(b3.getText().toString().equals(question.getAnswer())){

                                b3.setBackgroundColor(Color.GREEN);

                                Handler handler = new Handler(); // because we want delay to show right answer with green and wrong answer with red
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        correct++;
                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));

                                        updateQuestion();

                                    }
                                }, 1000);
                            }else {

                                //answer is wrong  then here we will find the correct answer and make it green.

                                wrong++;
                                b3.setBackgroundColor(Color.RED);

                                if(b1.getText().toString().equals(question.getAnswer())){
                                    b1.setBackgroundColor(Color.GREEN);
                                }
                                else if(b2.getText().toString().equals(question.getAnswer())){
                                    b2.setBackgroundColor(Color.GREEN);
                                }else{
                                    b4.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler =new Handler();
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
                            if(b4.getText().toString().equals(question.getAnswer())){

                                b4.setBackgroundColor(Color.GREEN);

                                Handler handler = new Handler(); // because we want delay to show right answer with green and wrong answer with red
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        correct++;
                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));

                                        updateQuestion();

                                    }
                                }, 1000);
                            }else {

                                //answer is wrong  then here we will find the correct answer and make it green.

                                wrong++;
                                b4.setBackgroundColor(Color.RED);

                                if(b1.getText().toString().equals(question.getAnswer())){
                                    b1.setBackgroundColor(Color.GREEN);
                                }
                                else if(b2.getText().toString().equals(question.getAnswer())){
                                    b2.setBackgroundColor(Color.GREEN);
                                }else{
                                    b3.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler =new Handler();
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
    }*/
}
