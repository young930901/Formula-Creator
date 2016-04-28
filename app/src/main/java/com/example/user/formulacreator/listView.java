package com.example.user.formulacreator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class listView extends AppCompatActivity {
    private String[] nothing = { "No Formula" };
    private FormulaCreate fm;
    private ArrayList<String> alist;
    private ListView lw;
    private ArrayAdapter arrayAdapter;
    static int p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        lw = (ListView) findViewById(R.id.listView);

        alist = new ArrayList<>();


        fm = new FormulaCreate();

        if(fm.getFormula().size()==0)
        {

            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nothing);
            lw.setAdapter(arrayAdapter);

        }
        else
        {
            for(int i=0; i<fm.getFormula().size();i++)
            {

                alist.add(fm.getFormula().get(i).toString());
            }


            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alist);
            lw.setAdapter(arrayAdapter);

            lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    p = position;
                    Intent i=new Intent(listView.this, Calculate.class);
                    startActivity(i);
                }

            });
        }

    }


}

