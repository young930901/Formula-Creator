package com.example.user.formulacreator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 4/12/2016.
 */
public class Formula implements Serializable{
    public ArrayList<Token> exprList= new ArrayList<>();

    public void add(Token expr)
    {
        exprList.add(expr);
    }

    public void reset()
    {
    }
    public void delete()
    {
        exprList.remove(exprList.size()-1);
    }
    public ArrayList<Token> getForm()
    {
        return exprList;
    }
    public String toString()
    {
        String s="";
        for(int i=0; i<exprList.size();i++)
        {
            s= s+exprList.get(i).content;
        }
        return s;
    }
}
