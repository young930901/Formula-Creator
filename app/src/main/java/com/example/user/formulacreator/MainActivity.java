package com.example.user.formulacreator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button createFormulaPg;
    Button loadPg;
    Button thisApp;
    FormulaCreate fc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null&&savedInstanceState.getSerializable("saved")!=null)
        {
                fc = (FormulaCreate)savedInstanceState.getSerializable("saved");
        }

        createFormulaPg = (Button)findViewById(R.id.button1);
        createFormulaPg.setOnClickListener(this);
        loadPg = (Button)findViewById(R.id.button2);
        loadPg.setOnClickListener(this);
        thisApp = (Button)findViewById(R.id.button3);
        thisApp.setOnClickListener(this);

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("saved", fc);
    }



    private void button1Click()
    {
        startActivity(new Intent("com.example.user.formulacreator.create"));
    }
    private void button2Click()
    {
        Intent i=new Intent(MainActivity.this, listView.class);
        startActivity(i);

    }
    public void button3Click()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("About This App");
        alertDialog.setMessage("This Application is made by Young Song in CPP for Senior Project. Enjoy!")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button1:
                button1Click();
                break;
            case R.id.button2:
                button2Click();
                break;
            case R.id.button3:
                button3Click();
                break;

        }
    }
}

