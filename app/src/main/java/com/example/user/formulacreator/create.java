package com.example.user.formulacreator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class create extends AppCompatActivity implements View.OnClickListener{

    AlertDialog levelDialog;
    Button operation;
    Button Variable;
    Button ETC;
    Button save;
    Button call;
    Button clear;
    Button condition;
    TextView txt;
    Formula formula;
    FormulaCreate fc;
    Token t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        formula = new Formula();

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
        condition = (Button) findViewById(R.id.condition);
        condition.setOnClickListener(this);

    }

    private void openOpeationDialog()
    {
        final String[] items = {"+","-","*","/","(",")","!","P","C","log"};
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
                txt.setText(formula.toString());
                levelDialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }
    private void openVariableDialog()
    {
        final String[] items = {"a","b","c","d","e","f","Var"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Variable");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                formula.add(new Token(items[item]));
                txt.setText(formula.toString());
                levelDialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }
    private void openETC()
    {
        final String[] items = {"sin","cos","tan","pi","e"};
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
                txt.setText(formula.toString());
                levelDialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }
    public static void serialize(Object obj, String fileName)
            throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }

    private void save() throws IOException {
            fc = new FormulaCreate();
            fc.putFormula(formula);


        SharedPreferences prefs = getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(fc);
        editor.putString("MyObject", json);
        editor.commit();

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

    private void call()
    {
        ArrayList<String> f = new ArrayList<>();
        fc = new FormulaCreate();
        for(int i=0; i<fc.size();i++)
        {
            f.add(fc.getFormula().get(i).toString());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Variable");
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        builder.setSingleChoiceItems(f.toArray(new String[f.size()]), -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                formula.append(fc.getFormula().get(i));
                txt.setText(formula.toString());
                dialog.dismiss();
            }
        });

        levelDialog = builder.create();
        levelDialog.show();

    }

    public void conditionList()
    {
        final String[] items = {"IF","ELSE","THEN", "==", "<=",">=","<",">","!=","||","&&"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Follw Format: IF condtion THEN Formula ELSE Formula");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                formula.add(new Token(items[item]));
                txt.setText(formula.toString());
                levelDialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();

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
            case R.id.call:
                call();
                break;
            case R.id.condition:
                conditionList();
                break;
        }
    }
}
