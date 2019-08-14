package com.minorproject.pariksha.Adpters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.minorproject.pariksha.AllMethods;
import com.minorproject.pariksha.Message;
import com.minorproject.pariksha.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageAdapterViewHolder> {


    Context context;
    List<Message> messages;
    DatabaseReference messagedb;

    public MessageAdapter(Context context, List<Message> messages, DatabaseReference messagedb){
        this.context = context;
        this.messagedb=messagedb;
        this.messages=messages;

    }
    @NonNull
    @Override
    public MessageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_cardview, parent, false);
        return new MessageAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapterViewHolder holder, int position) {

        Message message= messages.get(position);
        if (message.getName().equals(AllMethods.name)){

            holder.tvTitle.setText("You: "+message.getMessage());
            holder.tvTitle.setGravity(Gravity.START);
            holder.l1.setBackgroundColor(Color.parseColor("#c7dc85"));

        }else {
            holder.tvTitle.setText(message.getName()+":"+message.getMessage());
            //holder.ibDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageAdapterViewHolder extends  RecyclerView.ViewHolder{

        TextView tvTitle ;
        ImageButton ibDelete;
        LinearLayout l1;

        public MessageAdapterViewHolder(View itemView) {
            super(itemView);

            tvTitle= (TextView) itemView.findViewById(R.id.tvTitle);
            ibDelete = (ImageButton) itemView.findViewById(R.id.delete_icon);
            l1= (LinearLayout) itemView.findViewById(R.id.l1message);


            //when the user click on delete button then it takes the adapter position and remove its value from database
            ibDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    messagedb.child(messages.get(getAdapterPosition()).getKey()).removeValue();

                }
            });

        }
    }
}
