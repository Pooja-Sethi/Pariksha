package com.minorproject.pariksha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class GiveExamActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar mToolbar;
    Spinner spinner;
    EditText topic;
    Button giveTestbtn;
    public static String text1;
    public static String str1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_exam);

        final Spinner spinner = findViewById(R.id.choose_class_student);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.classes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        topic = (EditText) findViewById(R.id.topic_student);
        giveTestbtn = (Button) findViewById(R.id.give_TestBtn);


        mToolbar = (Toolbar) findViewById(R.id.give_test_toolbar );
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Enter Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(0xffffffff);

        giveTestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str1 = topic.getText().toString();

                Intent i =new Intent(GiveExamActivity.this, StartTestActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text1 = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
