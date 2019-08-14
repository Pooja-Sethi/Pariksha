package com.minorproject.pariksha;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TeacherActivity extends AppCompatActivity {

    private ImageView examImage, discussionImage;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        examImage = (ImageView) findViewById(R.id.exam);
        profileImage = (ImageView) findViewById(R.id.teacher_profile);
        discussionImage = (ImageView) findViewById(R.id.discussion_teacher);

        discussionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TeacherActivity.this, DiscussionActivity.class);
                startActivity(i);
                finish();
            }
        });

        examImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent prepareTest = new Intent(TeacherActivity.this, PrepareTest.class);
                startActivity(prepareTest);
                finish();
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent seeProfile = new Intent(TeacherActivity.this, ProfileActivity.class);
                startActivity(seeProfile);
                finish();
            }
        });
    }
}
