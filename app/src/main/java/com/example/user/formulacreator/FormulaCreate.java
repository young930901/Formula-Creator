package com.example.user.formulacreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 4/12/2016.
 */
public class FormulaCreate
{int p;
    public static ArrayList<Formula> formulas = new ArrayList<>();

    public static void putFormula(Formula s){ formulas.add(s);}

    public ArrayList<Formula> getFormula()
    {
        return formulas;
    }


    public int size()
    {
        return formulas.size();
    }


}
