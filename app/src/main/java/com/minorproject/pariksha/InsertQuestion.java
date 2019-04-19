package com.minorproject.pariksha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class InsertQuestion extends AppCompatActivity {

    Button addQuestionbtn;
    EditText questionText, option1, option2, option3, option4,answer;

    FirebaseAuth mAuth;
    private Toolbar mToolbar;

    DatabaseReference mRef;

    private RecyclerView mRecyclerView;

    private List<questions> mquestions;
   // FirebaseRecyclerAdapter<questions, questionsViewHolder>firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_question);

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference(PrepareTest.text).child(PrepareTest.str);//class number becomes the root of database
        mRef.keepSynced(true);

      /*  mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_questions);
        mRecyclerView.setLayoutManager(new LinearLayoutManager());*/

        mquestions = new ArrayList<>();

        questionText = (EditText) findViewById(R.id.write_question_insert);
        option1 = (EditText) findViewById(R.id.option1_insert);
        option2 = (EditText) findViewById(R.id.option2_insert);
        option3 = (EditText) findViewById(R.id.option3_insert);
        option4 = (EditText) findViewById(R.id.option4_insert);
        answer = (EditText) findViewById(R.id.correct_ans_insert);

        addQuestionbtn = (Button) findViewById(R.id.add_ques_insertbtn);

        mToolbar = (Toolbar)findViewById(R.id.topic_wise_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Insert Question");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(0xffffffff);

        addQuestionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(InsertQuestion.this, RetrieveQuestions.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

       /* FirebaseRecyclerAdapter<questions, questionsViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<questions, questionsViewHolder>(
                questions.class,
                R.layout.question_info,
                questionsViewHolder.class,
                mRef

        ) {
            @Override
            protected void populateViewHolder(questionsViewHolder viewHolder, questions model, int position) {

                //viewHolder.setQnum(model.getQnum());
                viewHolder.setQuestion(model.getQuestion());
                viewHolder.setOption1(model.getOption1());
                viewHolder.setOption2(model.getOption2());
                viewHolder.setOption3(model.getOption3());
                viewHolder.setOption4(model.getOption4());
                viewHolder.setCorrect_answer(model.getCorrect_answer());
            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        */
    }


    //Inner static class
   /* public static  class questionsViewHolder extends RecyclerView.ViewHolder{

        TextView ques, moption1, moption2, moption3, moption4, mans;
        //TextView qno;
        public questionsViewHolder(View itemView){
            super(itemView);

            ques = (TextView) itemView.findViewById(R.id.question_text);
            moption1 = (TextView) itemView.findViewById(R.id.option_1);
            moption2 = (TextView) itemView.findViewById(R.id.option_2);
            moption3 = (TextView) itemView.findViewById(R.id.option_3);
            moption4 = (TextView) itemView.findViewById(R.id.option_4);
            mans = (TextView) itemView.findViewById(R.id.ans_correct);
        }

        public void setQuestion(String question) {
            ques.setText(question);
        }

        public void setOption1(String option1) {
            moption1.setText(option1);
        }

        public void setOption2(String option2) {
            moption2.setText(option2);
        }

        public void setOption3(String option3) {
            moption3.setText(option3);
        }

        public void setOption4(String option4) {
            moption4.setText(option4);
        }

        public void setCorrect_answer(String correct_answer) {
            mans.setText(correct_answer);
        }
    }*/

}
