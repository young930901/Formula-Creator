package com.example.user.formulacreator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class load extends AppCompatActivity implements View.OnClickListener {

    Button delete;

    Button firstbttn;
    Button secondbttn;
    Button thirdbttn;
    Button fourthbttn;
    Button fifthbttn;
    Button sixthbttn;
    Button seventhbttn;
    Button eightbttn;

    TextView firstFormula;
    TextView secondFormula;
    TextView thirdFormula;
    TextView fourthFormula;
    TextView fifthFormula;
    TextView sixthFormula;
    TextView seventhFormula;
    TextView eigthFormula;

    TextView txtArray[];
    Button bttnArray[];
    FormulaCreate fc;
    Formula f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        fc = new FormulaCreate();
        f = new Formula();

        try {
            readTxtFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        txtArray = new TextView[8];
        bttnArray = new Button[8];

        txtArray[0] = firstFormula;
        txtArray[1] = secondFormula;
        txtArray[2] = thirdFormula;
        txtArray[3] = fourthFormula;
        txtArray[4] = fifthFormula;
        txtArray[5] = sixthFormula;
        txtArray[6] = seventhFormula;
        txtArray[7] = eigthFormula;


        bttnArray[0] = firstbttn;
        bttnArray[1] = secondbttn;
        bttnArray[2] = thirdbttn;
        bttnArray[3] = fourthbttn;
        bttnArray[4] = fifthbttn;
        bttnArray[5] = sixthbttn;
        bttnArray[6] = seventhbttn;
        bttnArray[7] = eightbttn;

        txtArray[0] = (TextView) findViewById(R.id.txt1);
        txtArray[1] = (TextView) findViewById(R.id.txt2);
        txtArray[2] = (TextView) findViewById(R.id.txt3);
        txtArray[3] = (TextView) findViewById(R.id.txt4);
        txtArray[4] = (TextView) findViewById(R.id.txt5);
        txtArray[5] = (TextView) findViewById(R.id.txt6);
        txtArray[6] = (TextView) findViewById(R.id.txt7);
        txtArray[7] = (TextView) findViewById(R.id.txt8);


        bttnArray[0] = (Button) findViewById(R.id.form1);
        bttnArray[0].setOnClickListener(this);
        bttnArray[1] = (Button) findViewById(R.id.form2);
        bttnArray[1].setOnClickListener(this);
        bttnArray[2] = (Button) findViewById(R.id.form3);
        bttnArray[2].setOnClickListener(this);
        bttnArray[3] = (Button) findViewById(R.id.form4);
        bttnArray[3].setOnClickListener(this);
        bttnArray[4] = (Button) findViewById(R.id.form5);
        bttnArray[4].setOnClickListener(this);
        bttnArray[5] = (Button) findViewById(R.id.form6);
        bttnArray[5].setOnClickListener(this);
        bttnArray[6] = (Button) findViewById(R.id.form7);
        bttnArray[6].setOnClickListener(this);
        bttnArray[7] = (Button) findViewById(R.id.form8);
        bttnArray[7].setOnClickListener(this);

        delete =  (Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);

        loadFormula();


    }

    private void loadFormula() {

        int i=0;
        for(String s : fc.getFormula())
        {
            txtArray[i].setText(s);
            i++;
        }

        for(int j=fc.getFormula().size();j<8;j++)
        {
            txtArray[j].setText("Empty");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete:
                try {
                    delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                startActivity(new Intent("com.example.user.formulacreator.Calculate"));
        }
    }

    public void delete() throws IOException {

        initializeList();
        FileWriter fwOb = new FileWriter("formulas.txt", false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("formulas.txt", Context.MODE_PRIVATE));
        for(String s: fc.getFormula())
        {
            outputStreamWriter.write(s+"\n");
            outputStreamWriter.close();
        }
        loadFormula();
    }
    private void deleteFromFile(int i)
    {

    }
    private void readTxtFile() throws IOException {
        fc = new FormulaCreate();
        //initializeList();
        InputStream inputStream = openFileInput("formulas.txt");

        if ( inputStream != null ) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";

            while ( (receiveString = bufferedReader.readLine()) != null ) {

                     fc.putFormula(receiveString);
                     fc.tekenize(receiveString);

            }
            inputStream.close();
        }

    }
    private void initializeList()
    {
        for(String s: fc.getFormula())
        {
            fc.getFormula().remove(0);
        }
    }


}

