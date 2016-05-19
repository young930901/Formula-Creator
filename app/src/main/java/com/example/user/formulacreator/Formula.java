package com.example.user.formulacreator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 4/12/2016.
 */
public class Formula implements Serializable{
    public ArrayList<Token> exprList;

    public Formula()
    {
        exprList = new ArrayList<>();
    }
    public Formula(ArrayList<Token> that)
    {
        exprList = new ArrayList<>();
        for(int i =0; i<that.size();i++)
        {
            exprList.add(that.get(i));
        }
    }
    public int validCondition()
    {
        int count =0;
        for(int i=0; i<exprList.size();i++)
        {
            if(exprList.get(i).getType()==Token.IF||exprList.get(i).getType()==Token.ELSE||exprList.get(i).getType()==Token.THEN)
                count++;
        }
        return count;
    }
    public int getIndex(String target)
    {
        int i =-1;
        for(int j =0; j<exprList.size();j++)
        {
            if(target==exprList.get(j).content)
            {
                i = j;
                break;
            }

        }
        return i;
    }

    public void add(Token expr)
    {
        exprList.add(expr);
    }
    public void append(Formula f)
    {
        for(int i=0; i<f.getForm().size();i++)
        {
            this.exprList.add(f.getForm().get(i));
        }

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
            s= s+" "+exprList.get(i).content;
        }
        return s;
    }
}
