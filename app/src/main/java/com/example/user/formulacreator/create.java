package com.example.user.formulacreator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class create extends AppCompatActivity implements View.OnClickListener{

    AlertDialog levelDialog;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        txt = (TextView)findViewById(R.id.textView3);

        button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button5 = (Button)findViewById(R.id.button5);
        button5.setOnClickListener(this);
        button6 = (Button)findViewById(R.id.button6);
        button6.setOnClickListener(this);
        button7 = (Button)findViewById(R.id.button7);
        button7.setOnClickListener(this);
        button8 = (Button)findViewById(R.id.button8);
        button8.setOnClickListener(this);
        button9 = (Button)findViewById(R.id.button9);
        button9.setOnClickListener(this);


    }

    private void button4Click()
    {
        final String[] items = {" + "," - "," * "," / "};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Operation");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();

        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                switch (item) {
                    case 0:
                        txt.setText(txt.getText() + items[item]);
                        break;
                    case 1:
                        txt.setText(txt.getText() + items[item]);
                        break;
                    case 2:
                        txt.setText(txt.getText() + items[item]);
                        break;
                    case 3:
                        txt.setText(txt.getText() + items[item]);
                        break;

                }
                levelDialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }
    private void button5Click()
    {
        final String[] items = {" a "," b "," c "," d "};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Variable");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                switch (item) {
                    case 0:
                        txt.setText(txt.getText()+items[item]);
                        break;
                    case 1:
                        txt.setText(txt.getText()+items[item]);
                        break;
                    case 2:
                        txt.setText(txt.getText()+items[item]);
                        break;
                    case 3:
                        txt.setText(txt.getText()+items[item]);
                        break;

                }
                levelDialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }
    private void button7Click()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("eqn", (String) txt.getText()); //InputString: from the EditText
        editor.commit();

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Your Eqn is saved. Go to the menu and try it!")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        create.this.finish();
                        dialog.cancel();
                    }
                }).create();
        alertDialog.show();

    }
    private void button9Click()
    {
        txt.setText("");

    }
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button4:
                button4Click();
                break;
            case R.id.button5:
                button5Click();
                break;
            case R.id.button7:
                button7Click();
                break;
            case R.id.button9:
                button9Click();
                break;
        }
    }
}
