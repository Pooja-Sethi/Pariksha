package com.minorproject.pariksha;

import android.content.Intent;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class PrepareTest extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static Spinner spinner;
    private EditText topic;
    private Button preparebtn;

    private Toolbar mToolbar;

    public static String text;
    public static String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_test);
        final Spinner spinner = findViewById(R.id.choose_class);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.classes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        topic = (EditText)findViewById(R.id.topic);
        preparebtn = (Button)findViewById(R.id.prepareTestBtn);

        mToolbar = (Toolbar) findViewById(R.id.prepareTest_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Prepare Test");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(0xffffffff);

        preparebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str = topic.getText().toString();
                //text=text+" "+str;
               // TopicTestActivity.message =  text;
                Intent i = new Intent(PrepareTest.this,TopicTestActivity.class );
                //i.putExtra("class" , text);
               //i.putExtra("Topic", str);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
