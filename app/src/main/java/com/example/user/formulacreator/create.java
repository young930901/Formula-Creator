package com.example.user.formulacreator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class create extends AppCompatActivity implements View.OnClickListener{

    AlertDialog levelDialog;
    Button operation;
    Button Variable;
    Button ETC;
    Button save;
    Button call;
    Button clear;
    TextView txt;
    Formula formula = new Formula();
    FormulaCreate fc;
    Token t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        txt = (TextView)findViewById(R.id.textView3);

        operation = (Button)findViewById(R.id.operation);
        operation.setOnClickListener(this);
        Variable = (Button)findViewById(R.id.variable);
        Variable.setOnClickListener(this);
        ETC = (Button)findViewById(R.id.etc);
        ETC.setOnClickListener(this);
        save = (Button)findViewById(R.id.save);
        save.setOnClickListener(this);
        call = (Button)findViewById(R.id.call);
        call.setOnClickListener(this);
        clear = (Button)findViewById(R.id.clear);
        clear.setOnClickListener(this);


    }

    private void openOpeationDialog()
    {
        final String[] items = {"+","-","*","/","(",")","!","P","C","Sum","log"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Operation");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();

        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                t = new Token(items[item]);
                formula.add(t);
                txt.setText(txt.getText() + items[item]);
                levelDialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }
    private void openVariableDialog()
    {
        final String[] items = {" a "," b "," c "," d "," e "," f "," Var "};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Variable");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                t = new Token(items[item]);
                formula.add(t);
                txt.setText(txt.getText() + items[item]);
                levelDialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }
    private void openETC()
    {
        final String[] items = {"sin"," cos", "tan", "cot", "cosec","Pi(3.14)", "e(2.718)"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Variable");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                t = new Token(items[item]);
                formula.add(t);
                txt.setText(txt.getText() + items[item]);
                levelDialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }
    private void save() throws IOException {

        fc = new FormulaCreate();




            fc.putFormula(formula);


            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("formulas.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(formula.getForm()+"\n");
            outputStreamWriter.close();

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Formula Created");
            alertDialog.setMessage("Your Eqn is saved. Go to the menu and try it!")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            create.this.finish();
                            dialog.cancel();
                        }
                    }).create();
            alertDialog.show();

    }


    private void clear()
    {
        formula.delete();
        txt.setText(formula.toString());

    }
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.operation:
                openOpeationDialog();
                break;
            case R.id.variable:
                openVariableDialog();
                break;
            case R.id.save:
                try {
                    save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.etc:
                openETC();
                break;
            case R.id.clear:
                clear();
                break;
        }
    }
}
