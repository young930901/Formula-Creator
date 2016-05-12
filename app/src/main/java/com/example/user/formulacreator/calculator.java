package com.example.user.formulacreator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by user on 5/3/2016.
 */
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;



public class calculator
{
    private static ArrayList<Token> unprocessed;
    private static ArrayList<Token> infix;
    private static ArrayList<Token> postfix;
    Calculate c;
    private Stack<Token> opstack;
    private Stack<Token> parenthesis;
    private Stack<BinaryTree<Token>> binstack;


    public calculator(ArrayList<Token> exp)
    {
        unprocessed = exp;
        infix = new ArrayList<>();
        postfix = new ArrayList<>();
        binstack = new Stack<BinaryTree<Token>>();
        opstack = new Stack<Token>();
        parenthesis = new Stack<Token>();
    }
    public ArrayList<Token> getOriginal()
    {
        return unprocessed;
    }
    public ArrayList<Token> getInfix()
    {
        return infix;
    }
    public ArrayList<Token> getPostfix()
    {
        return postfix;
    }
    public void preprocessor()
    {
        boolean error = false;

        for(int i=0;i<unprocessed.size();i++)
        {

            Token token = unprocessed.get(i);
            Token nexttoken = new Token();
            boolean lastchar = false;
            boolean isTokenDigit = false;
            boolean isNextTokenDigit = false;;

            if(i==unprocessed.size()-1)
            {

                lastchar = true;
            }
            else
            {
                nexttoken = unprocessed.get(i+1);
            }


            if(!lastchar)
            {
                isTokenDigit = token.getType() == Token.NUMBER;
                isNextTokenDigit = nexttoken.getType() == Token.NUMBER;
                if((isTokenDigit && nexttoken.getType()==Token.LPAREN)
                        || (token.getType()==Token.RPAREN && isNextTokenDigit)
                        || (token.getType()==Token.RPAREN) && (nexttoken.getType()==Token.LPAREN)
                        || (token.getType()==Token.RPAREN && nexttoken.getType()==Token.POWER)
                        || (isTokenDigit && nexttoken.getType()==Token.SIN)
                        || (isTokenDigit && nexttoken.getType()==Token.COS)
                        || (isTokenDigit && nexttoken.getType()==Token.TAN))
                {
                    infix.add(token);
                    infix.add(new Token("*"));
                    infix.add(nexttoken);
                    i++;

                }
                else
                {
                    infix.add(token);
                }
            }
            else if(lastchar)
            {
                infix.add(token);
                //temp.add(token)
            }
        }
        if(error)
            infix = postfix = null;

    }

    private int precedence(Token token)
    {
        int result = 0;
        if(token.getType()==Token.SIN||token.getType()==Token.TAN||token.getType()==Token.COS||token.getType()==Token.LOG)
        {
            result = 3;
        }
        else if(token.getType()==Token.TIMES || token.getType()==Token.DIVIDE)
        {
            result = 2;
        }
        else if(token.getType()==Token.PLUS||token.getType()==Token.MINUS)
        {
            result = 1;
        }

        return result;
    }
    private void processOp(Token op)
    {
        if(opstack.empty() || op.getType()==Token.LPAREN)
        {
            opstack.push(op);
        }
        else
        {
            Token topop = opstack.peek();
            if(precedence(op)>precedence(topop))
            {
                opstack.push(op);
            }
            else
            {
                while(!opstack.empty() && precedence(op) <= precedence(topop))
                {
                    opstack.pop();
                    if(topop.getType()==Token.LPAREN)
                    {
                        break;
                    }
                    postfix.add(topop);
                    if(!opstack.empty())
                    {
                        topop=opstack.peek();
                    }
                }
                if(op.getType()!=Token.RPAREN)
                    opstack.push(op);
            }
        }
    }
    public void infix2postfix()
    {
        boolean error = false;

        if(infix!=null)
        {
            for(int i=0;i<infix.size();i++)
            {
                Token token = infix.get(i);

                try
                {
                    if(token.getType()==Token.LPAREN)
                    {
                        parenthesis.push(token);
                    }
                    if(token.getType()==Token.RPAREN)
                    {
                        parenthesis.pop();
                    }
                }
                catch(EmptyStackException e)
                {
                    System.out.println("Unbalanced parenthesis.");
                    postfix = null;
                    error = true;
                    break;
                }

                if(token.getType()==Token.NUMBER)
                {
                    postfix.add(token);
                }
                else if(token.isOp())
                {
                    processOp(token);
                }

            }
            if(!parenthesis.empty())
            {
                System.out.println("Unbalanced parenthesis.");
                postfix = null;
                error = true;

            }
            if(!error)
            {
                while(!opstack.empty())
                {
                    Token op = opstack.pop();
                    if(op.getType()==Token.LPAREN||op.getType()==Token.RPAREN)
                    {

                    }
                    else postfix.add(op);

                }

            }
        }
    }
    public BinaryTree<Token> buildExpressionTree()
    {
        if(postfix!=null)
        {
            Iterator<Token> elements = postfix.iterator();
            Token token;
            while(elements.hasNext())
            {
                token = elements.next();
                if(token.getType()==Token.NUMBER)
                {
                    BinaryTree<Token> element = new BinaryTree<Token>(token,null,null);
                    binstack.push(element);
                }
                else if(token.getType()==Token.E||token.getType()==Token.PI)
                {
                    BinaryTree<Token> element = new BinaryTree<Token>(token,null,null);
                    binstack.push(element);
                }
                else if(token.getType()==Token.SIN
                        ||token.getType()==Token.COS
                        ||token.getType()==Token.TAN
                        ||token.getType()==Token.LOG)

                {
                    BinaryTree<Token> left  = binstack.pop();
                    BinaryTree<Token> element = new BinaryTree<>(token,left,null);
                    binstack.push(element);
                }
                else
                {
                    BinaryTree<Token> right = binstack.pop();
                    BinaryTree<Token> left = binstack.pop();
                    BinaryTree<Token> element = new BinaryTree<Token>(token,left,right);

                    binstack.push(element);
                }
            }
            return binstack.pop();

        }
        else
            return null;

    }

    public double evalExpressionTree(BinaryTree<Token> eTree)
    {
        if(eTree == null)
            return 0;
        else if(eTree.isLeaf())
            return eTree.root.data.getValue();
        else
        {
            Token op = eTree.root.data;
            //Token op = eTree.root.data
            double left = evalExpressionTree(eTree.getLeftSubtree());
            double right = evalExpressionTree(eTree.getRightSubtree());


            return evaluate(op, left, right);
        }
    }

    /**Evaluates two numbers and returns result
     *
     * @param op operator
     * @param left number
     * @param right number
     * @return result
     */
    private double evaluate(Token op, double left,double right)
    {
        // evaluate(Token op, double left, double right)
        double result = 0;

        switch(op.getType())
        {
            case Token.PLUS : result = left+right; break;
            case Token.MINUS : result = left-right; break;
            case Token.TIMES : result = left*right; break;
            case Token.DIVIDE : result = left/right; break;
            case Token.SIN: result = Math.sin(left); break;
            case Token.COS: result = Math.cos(left); break;
            case Token.TAN: result = Math.tan(left); break;
            case Token.LOG: result = Math.log(left);break;

        }



        return result;
    }

}
