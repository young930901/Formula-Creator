package com.example.user.formulacreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 4/12/2016.
 */
public class Formula {
    public ArrayList<String> exprList= new ArrayList<>();

    public void add(String expr)
    {
        if(exprList.size()==0)
        {
            exprList.add(expr);
        }
        else
        {
            exprList.add(","+expr);
        }

    }
    public void reset()
    {

    }
    public void delete()
    {
        exprList.remove(exprList.size()-1);
    }
    public String getForm()
    {
        String formula="";
        for(String s :exprList)
        {
            formula+=s;
        }
        return formula;
    }
    public String toString()
    {
        String s="";
        for(String st: exprList)
        {
            if(st.startsWith(",")==true)
            {
                s+=st.substring(1,st.length());
            }
            else
                s+=st;
        }
        return s;
    }
}
