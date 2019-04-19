package com.minorproject.pariksha;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RetrieveQuestions extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;
    private List<questions> mquestions;
    private DatabaseReference myRef;
    private Toolbar mToolbar;
    private Button removebtn;
    private Button insertbtn;
    private ImageView back_arrow;
    FirebaseRecyclerAdapter<questions, questionsViewHolder>firebaseRecyclerAdapter;
    private Object questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_questions);

        myRef = FirebaseDatabase.getInstance().getReference().child(PrepareTest.text).child(PrepareTest.str);
        myRef.keepSynced(true);

        //firebaseRecyclerAdapter
        FirebaseRecyclerAdapter<questions, questionsViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<questions, questionsViewHolder>(
                questions.class,
                R.layout.question_info,
                questionsViewHolder.class,
                myRef

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


        removebtn = (Button) findViewById(R.id.remove_question);
        insertbtn = (Button) findViewById(R.id.insert_question);

        back_arrow = (ImageView) findViewById(R.id.back_arrow_recyclerview);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_questions);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mquestions = new ArrayList<>();
        loadRecyclerViewData();

        mToolbar = (Toolbar) findViewById(R.id.recycler_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("All Questions: "+PrepareTest.str);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(0xffffffff);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RetrieveQuestions.this, TopicTestActivity.class);
                startActivity(i);
                finish();
            }
        });

        removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert();
            }
        });

        insertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // insert_prompt();
               updateData(mquestions);
            }
        });

        firebaseRecyclerAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }



    @Override
    protected void onStart() {
        super.onStart();
        //this.firebaseRecyclerAdapter.startListening();
    }

    private void updateData(List<com.minorproject.pariksha.questions> mquestions) {


        this.mquestions.clear();
        this.mquestions.addAll(mquestions);
        //firebaseRecyclerAdapter.notifyDataSetChanged();
    }

    private void loadRecyclerViewData() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading data..");
        progressDialog.show();

    }

   private void alert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm!");
        builder.setMessage("You are about to delete a question from the firebase database.Do you really want to proceed?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                prompt();

               // Toast.makeText(RetrieveQuestions.this, "You've choosen to delete a question.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(RetrieveQuestions.this, "You've chnged your mind to delete the question.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    //Inner static class
   public static  class questionsViewHolder extends RecyclerView.ViewHolder{

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
            //qno= (TextView)itemView.findViewById(R.id.ques_no);
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

       /* public void setQnum(String qnum) {
            qno.setText(qnum);
        }*/

        public void setCorrect_answer(String correct_answer) {
            mans.setText(correct_answer);
           // Toast.makeText(, "", Toast.LENGTH_SHORT).show();
        }
    }

    /*public void updateData(List<questions> nquestions){
        mquestions.clear();
        mquestions.addAll(nquestions);
        firebaseRecyclerAdapter.notifyDataSetChanged();
    }*/


    private void prompt() {

        final EditText editText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete!");
        builder.setMessage("Enter Question no. you want to remove..");
        builder.setCancelable(false);
        builder.setView(editText);
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int position = Integer.parseInt(editText.getText().toString());

                removeQues(position);
            }
        });
        builder.show();
    }

   private void removeQues(int position) {

       myRef.child(String.valueOf(position)).removeValue();
        //firebaseRecyclerAdapter.getRef(position).removeValue();
        // adapter.notifyItemRemoved(position);
        //firebaseRecyclerAdapter.notifyDataSetChanged();
        Toast.makeText(this, "item deleted!", Toast.LENGTH_SHORT).show();
    }
}
// due to the following line there appears a large gaps between the items of recycler view
// mRecyclerView.setHasFixedSize(true);