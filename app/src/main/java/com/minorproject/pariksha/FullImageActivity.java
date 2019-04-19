package com.minorproject.pariksha;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

public class FullImageActivity extends AppCompatActivity {

   // private ImageView mImage;
    ImageView mArrow;
    Uri uri;
    String stringUri;

    PhotoView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image); //to set xml file into java file

        mImage = (PhotoView) findViewById(R.id.full_image);
       // mImage = (ImageView)findViewById(R.id.full_image);
        mArrow = (ImageView) findViewById(R.id.back_arrow);

    //   stringUri = uri.toString();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("res_id")) {
            uri = Uri.parse(bundle.getString("res_id"));
            mImage.setImageURI(uri);
        }

        mArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FullImageActivity.this, ProfileActivity.class);
                i.putExtra("full" , mImage.toString());
                startActivity(i);
                finish();
            }
        });
    }
}
