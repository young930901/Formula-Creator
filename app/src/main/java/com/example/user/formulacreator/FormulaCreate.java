package com.example.user.formulacreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 4/12/2016.
 */
public class FormulaCreate
{
    public static ArrayList<String> formulas = new ArrayList<>();
    public static ArrayList<String> formulasWithComma = new ArrayList<>();

    public static void putFormula(String s){ formulasWithComma.add(s);}



    public ArrayList<String> getFormula()
    {
        return formulas;
    }
    public ArrayList<String> getFormulasWithCommai()
    {
        return formulasWithComma;
    }
    public int size()
    {
        return formulas.size();
    }

    public void tekenize(String s)
    {
        String tokens[] = s.split(",");
        String form="";

        for(String token: tokens)
        {
            form += token;
        }

        formulas.add(form);
    }

}
