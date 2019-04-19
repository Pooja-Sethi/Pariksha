package com.minorproject.pariksha;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter <MyAdapter.MyViewHolder>{

    Context context;
    ArrayList<questions> mquestions;

    public MyAdapter(Context c, ArrayList<questions> q){
        context = c;
        mquestions = q;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.question_info,parent, false)) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mquestion.setText(mquestions.get(position).getQuestion());
        holder.moption1.setText(mquestions.get(position).getOption1());
        holder.moption2.setText(mquestions.get(position).getOption2());
        holder.moption3.setText(mquestions.get(position).getOption3());
        holder.moption4.setText(mquestions.get(position).getOption4());
        holder.mans.setText(mquestions.get(position).getCorrect_answer());
    }

    @Override
    public int getItemCount() {
        return mquestions.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mquestion, moption1, moption2, moption3, moption4, mans;

        public MyViewHolder(View itemView) {
            super(itemView);

            mquestion = (TextView) itemView.findViewById(R.id.question_text);
            moption1 = (TextView) itemView.findViewById(R.id.option_1);
            moption2 = (TextView) itemView.findViewById(R.id.option_2);
            moption3 = (TextView) itemView.findViewById(R.id.option_3);
            moption4 = (TextView) itemView.findViewById(R.id.option_4);
            mans = (TextView) itemView.findViewById(R.id.correct_ans);
        }
    }
}
