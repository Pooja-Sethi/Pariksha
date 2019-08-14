package com.minorproject.pariksha;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//Prepare test and store it in firebase database.

public class TopicTestActivity extends AppCompatActivity {


   /* TextView t1_question;
    Button b1,b2,b3,b4;
    public static String message;*/

    private Toolbar mToolbar;
    public static String str;

    FirebaseAuth mAuth;
   /* int total =1;
    int correct = 0, wrong=0;

    DatabaseReference dref; */

   EditText questionText, option1, option2, option3, option4,answer;
   //public static  TextView count;
   public static TextView qnum;//how many questions currently added by the teacher
   public static int num;//to count number of questions so that it does not exceed 5
   Button Submitbtn;
   //String num1;

   DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_test);


        mAuth = FirebaseAuth.getInstance();
        str=PrepareTest.str;
        mToolbar = (Toolbar)findViewById(R.id.topic_wise_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Test Topic- "+str);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(0xffffffff);

        questionText = (EditText) findViewById(R.id.write_question);
        option1 = (EditText) findViewById(R.id.option1);
        option2 = (EditText) findViewById(R.id.option2);
        option3 = (EditText) findViewById(R.id.option3);
        option4 = (EditText) findViewById(R.id.option4);
        answer = (EditText) findViewById(R.id.correct_ans);
        //count = (TextView) findViewById(R.id.count);
        Submitbtn = (Button) findViewById(R.id.submit_ques);
        qnum =(TextView) findViewById(R.id.ques);

        num=1 ;

        qnum.setText(String.valueOf(num));

        mRef = FirebaseDatabase.getInstance().getReference(PrepareTest.text);//class number becomes the root of database

        Submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    //count.setText(String.valueOf(num));
                    String id = String.valueOf(num);//question number is the child of topic.
                    Question mquestion = new Question(qnum.getText().toString(), questionText.getText().toString(), option1.getText().toString(), option2.getText().toString(),
                            option3.getText().toString(), option4.getText().toString(), answer.getText().toString());
                    mRef.child(str).child(id).setValue(mquestion);

                   // qnum.setText(String.valueOf(Integer.parseInt(id)+1));
                    num++;
                    qnum.setText(String.valueOf(num));

                    questionText.setText("");
                    option1.setText("");
                    option2.setText("");
                    option3.setText("");
                    option4.setText("");
                    answer.setText("");
                    Toast.makeText(TopicTestActivity.this, "Question added", Toast.LENGTH_SHORT).show();

            }
        });
       /* topic_text = (TextView) findViewById(R.id.topicwisetext);
        topic_text.setText(message);
        */
       /* Intent i = getIntent();
        String str = i.getStringExtra("Class");
        topic_text.setText(str);
        */
    }

   /* @Override
    protected void onRestart() {
        super.onRestart();

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null && bundle.containsKey("count")){
            num1 = bundle.getString("count");
            num = Integer.parseInt(num1);
            count.setText(String.valueOf(num));
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){
            case R.id.item1: {
                //Toast.makeText(TopicTestActivity.this, "Item 1 selected", Toast.LENGTH_LONG).show();
                Intent i = new Intent(TopicTestActivity.this, RetrieveQuestions.class);
                startActivity(i);
                finish();
                return true;
            }

            case R.id.item2: {
                mAuth.signOut();
                finish();
                startActivity(new Intent(TopicTestActivity.this, LoginActivity.class));
                return true;
            }

            case R.id.item3: {
                mAuth.signOut();
                finish();
                startActivity(new Intent(TopicTestActivity.this, LoginActivity.class));
                return true;
            }

                default:
                    return super.onOptionsItemSelected(item);
        }

    }
}
/*     <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/ques_add"
        android:layout_below="@+id/ans_layout2"
        android:id="@+id/added_ques"
        android:layout_marginStart="100dp"
        android:textSize="16sp"/>

    <TextView
        android:id="@+id/count"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_toRightOf="@+id/added_ques"
        android:layout_marginStart="10dp"
        android:layout_marginTop="-10dp"
        android:layout_below="@+id/ans_layout2"
        android:text="@string/_0"/>*/