package com.minorproject.pariksha;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minorproject.pariksha.Adpters.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

public class DiscussionActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference messagedb,mRef;
    MessageAdapter messageAdapter;
    addInformation user;
    List<Message> messages;

    RecyclerView rvmessage;
    EditText etMessage;
    ImageButton imgbtn;
    private Toolbar mToolbar;

    String dkey;
    String dName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

       /* mToolbar = (Toolbar) findViewById(R.id.discussion_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Discussion");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(0xffffffff);
*/
        init();
    }


    private void init() {

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        user  = new addInformation();
        dkey = LoginActivity.mkey.getText().toString();

        mRef = FirebaseDatabase.getInstance().getReference("Users");
        //dName = Main2Activity.nameStr;
         mRef.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 dName = dataSnapshot.child(dkey).child("name").getValue().toString();
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });

        rvmessage = (RecyclerView) findViewById(R.id.recyclerview_discussion);
        etMessage = (EditText) findViewById(R.id.Message);
        imgbtn = (ImageButton) findViewById(R.id.btnsend);
        imgbtn.setOnClickListener(this);
        messages = new ArrayList<>();

    }

    @Override
    public void onClick(View v) {

        if (!TextUtils.isEmpty(etMessage.getText().toString())){

            Message message = new Message(etMessage.getText().toString(), dName);
            etMessage.setText("");
            messagedb.push().setValue(message);

        }else {
            Toast.makeText(getApplicationContext(), "You cannot send blank message", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        user.setEmail(currentUser.getEmail());
        user.setName(dName);

        mDatabase.getReference("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                user = dataSnapshot.getValue(addInformation.class);
                //user.setId(dkey);
               AllMethods.name = dName;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        messagedb = mDatabase.getReference("messages");
        messagedb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //this method will display all the messages
                Message message = dataSnapshot.getValue(Message.class);
                message.setKey(dataSnapshot.getKey());
                messages.add(message);
                displayMessages(messages);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Message message = dataSnapshot.getValue(Message.class);
                message.setKey(dataSnapshot.getKey());

                List<Message> newMessages = new ArrayList<Message>();

                for(Message m : messages){

                    if (m.getKey().equals(message.getKey())){
                        newMessages.add(message);

                    }else {
                        newMessages.add(m);

                    }
                }

                messages = newMessages;

                displayMessages(messages); //message list
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                Message message = dataSnapshot.getValue(Message.class);
                message.setKey(dataSnapshot.getKey());

                List<Message> newMessages = new ArrayList<Message>();

                for(Message m:messages){

                    if (!m.getKey().equals(message.getKey())){
                        newMessages.add(m);
                    }
                }

                messages = newMessages;
                displayMessages(messages);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();
        messages = new ArrayList<>();

    }

    private void displayMessages(List<Message> messages) {
        rvmessage.setLayoutManager(new LinearLayoutManager(DiscussionActivity.this));
        rvmessage.setHasFixedSize(true);
        messageAdapter = new MessageAdapter(DiscussionActivity.this, messages, messagedb);
        rvmessage.setAdapter(messageAdapter);
    }
}
