package com.example.user.formulacreator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button button1;
    Button button2;
    Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(this);

    }

    private void button1Click()
    {
        startActivity(new Intent("com.example.user.formulacreator.create"));
    }
    private void button2Click()
    {
        startActivity(new Intent("com.example.user.formulacreator.load"));
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

