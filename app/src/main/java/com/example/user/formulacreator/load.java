package com.example.user.formulacreator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class load extends AppCompatActivity implements View.OnClickListener{

    Button form1;
    Button form2;
    Button form3;
    Button form4;
    Button form5;
    Button form6;
    Button form7;
    Button form8;

    TextView txt1;
    TextView txt2;
    TextView txt3;
    TextView txt4;
    TextView txt5;
    TextView txt6;
    TextView txt7;
    TextView txt8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String data = prefs.getString("eqn", "Empty");




        txt1 = (TextView)findViewById(R.id.txt1);
        txt2 = (TextView)findViewById(R.id.txt2);
        txt3 = (TextView)findViewById(R.id.txt3);
        txt4 = (TextView)findViewById(R.id.txt4);
        txt5 = (TextView)findViewById(R.id.txt5);
        txt6 = (TextView)findViewById(R.id.txt6);
        txt7 = (TextView)findViewById(R.id.txt7);
        txt8 = (TextView)findViewById(R.id.txt8);

        if(txt1.getText().equals("Empty"))
            txt1.setText(data);
        else if(txt2.getText().equals("Empty"))
            txt2.setText(data);



        form1 = (Button)findViewById(R.id.form3);
        form1.setOnClickListener(this);
        form2 = (Button)findViewById(R.id.form2);
        form2.setOnClickListener(this);
        form3 = (Button)findViewById(R.id.form4);
        form3.setOnClickListener(this);
        form4 = (Button)findViewById(R.id.form8);
        form4.setOnClickListener(this);
        form5 = (Button)findViewById(R.id.form1);
        form5.setOnClickListener(this);
        form6 = (Button)findViewById(R.id.form7);
        form6.setOnClickListener(this);
        form7 = (Button)findViewById(R.id.form6);
        form7.setOnClickListener(this);
        form8 = (Button)findViewById(R.id.form5);
        form8.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}
