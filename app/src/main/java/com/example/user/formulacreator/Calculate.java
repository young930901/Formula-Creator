package com.example.user.formulacreator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

public class Calculate extends AppCompatActivity implements View.OnClickListener {
    protected static final String AndroidCoding= null;
    TextView formula;
    TextView result;
    Button calculate;
    Formula form;
    FormulaCreate fc;
    static double value;
    static private ArrayList<Token> tokens;

    private static ArrayList<Token> unprocessed;
    private static ArrayList<Token> infix;
    private static ArrayList<Token> postfix;
    private Stack<Token> opstack;
    private Stack<Token> parenthesis;
    private Stack<BinaryTree<Token>> binstack;
    private HashMap<String, Double> hmap = new HashMap<String, Double>();
    AlertDialog.Builder alert;

    private ArrayList<Token> condition;
    private ArrayList<Token> thenStmt;
    private ArrayList<Token> elseStmt;

    private double cond = 1.0;

    private boolean isCond = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate2);

        fc = new FormulaCreate();
        form = new Formula(fc.getFormula().get(listView.p).getForm());
        tokens = new ArrayList<>();



        formula = (TextView) findViewById(R.id.textView2);
        result = (TextView) findViewById(R.id.result);
        formula.setText(form.toString());
        calculate = (Button) findViewById(R.id.button);
        calculate.setOnClickListener(this);

        for(int i=0; i<form.getForm().size();i++)
        {
            tokens.add(form.getForm().get(i));
        }
                for(int i = tokens.size()-1 ;i>=0;i--) {
                    if (tokens.get(i).getType() == Token.NUMBER) {
                        if(hmap.containsKey(tokens.get(i).content)) {
                            tokens.get(i).putValue(hmap.get(tokens.get(i).content));
                        }
                        else
                        {
                            askNumb(i);
                        }

                        result.setText(String.valueOf(tokens.get(i).getValue()));
                        //result.setText(String.valueOf(value));

                    }
                }


        if(form.getIndex("IF")!=-1||form.getIndex("ELSE")!=-1||form.getIndex("THEN")!=-1)
        {
            if(form.validCondition()>3)
            {
                result.setText("INVALID CONDITIONAL");
            }
            else if(form.getIndex("IF")!=-1&&form.getIndex("ELSE")!=-1&&form.getIndex("THEN")!=-1)
            {
                isCond = true;
                condition = new ArrayList<>();
                thenStmt = new ArrayList<>();
                elseStmt = new ArrayList<>();

                for(int i=1; i<form.getIndex("THEN");i++)
                {
                    condition.add(form.getForm().get(i));
                }
                for(int i=form.getIndex("THEN")+1; i<form.getIndex("ELSE");i++)
                {
                    thenStmt.add(form.getForm().get(i));
                }
                for(int i=form.getIndex("ELSE")+1; i<form.getForm().size();i++)
                {
                    elseStmt.add(form.getForm().get(i));
                }


            }
            else
            {
                result.setText("INVALID CONDITIONAL");
            }

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                if(isCond==true)
                {
                    calculator(condition);
                    preprocessor();
                    if(infix==null||postfix==null)
                    {
                        result.setText("INVALID FORMULA");
                    }
                    else
                    {
                        infix2postfix();
                        BinaryTree eTree = buildExpressionTree();

                        if(evalExpressionTree(eTree)==1)
                        {
                            calculator(thenStmt);
                            preprocessor();
                            if(infix==null||postfix==null)
                            {
                                result.setText("INVALID FORMULA");
                            }
                            else
                            {
                                infix2postfix();
                                BinaryTree eTree2 = buildExpressionTree();
                                result.setText(String.valueOf(evalExpressionTree(eTree2)));
                            }
                        }
                        else if(evalExpressionTree(eTree)==0)
                        {
                            calculator(elseStmt);
                            preprocessor();
                            if(infix==null||postfix==null)
                            {
                                result.setText("INVALID FORMULA");
                            }
                            else
                            {
                                infix2postfix();
                                BinaryTree eTree2 = buildExpressionTree();
                                result.setText(String.valueOf(evalExpressionTree(eTree2)));
                            }
                        }
                        else
                        {
                            result.setText("INVALID FORMULA");
                        }
                    }
                }

                else {
                    calculator(tokens);
                    preprocessor();
                    infix2postfix();
                    if(infix==null||postfix==null)
                    {
                        result.setText("INVALID FORMULA");
                    }
                    else
                    {
                        BinaryTree eTree2 = buildExpressionTree();
                        result.setText(String.valueOf(evalExpressionTree(eTree2)));
                    }
                    //result.setText(String.valueOf(hmap.containsKey("a")));
                }
                break;
        }
    }

    public void askNumb(final int i)
    {
        String s;

        alert = new AlertDialog.Builder(this);
        alert.setTitle("Put value of variable for " + tokens.get(i).content);
        final EditText edit = new EditText(this);
        edit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        alert.setView(edit);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface dialog, int whichButton)
            {
                    try {
                        value = Double.parseDouble(edit.getText().toString());
                        hmap.put(tokens.get(i).content, value);
                        tokens.get(i).putValue(value);

                        SharedPreferences.Editor editor = getSharedPreferences(AndroidCoding, MODE_PRIVATE).edit();
                        editor.putString("AC", edit.getText().toString());
                        editor.commit();
                        dialog.dismiss();
                    }
                    catch(NumberFormatException e)
                    {
                        result.setText(e.getMessage());
                        tokens.get(i).putValue(0);
                    }
            }
        };
        alert.setPositiveButton("Ok", listener);
        alert.create();
        alert.show();

        SharedPreferences prefs = getSharedPreferences(AndroidCoding, MODE_PRIVATE);
        s = prefs.getString("AC", "nothing/no value");
        value = Double.parseDouble(s);
        hmap.put(tokens.get(i).content, value);

    }
    public void setValue(double d,int i)
    {
        hmap.put(tokens.get(i).content, d);
//        result.setText(String.valueOf(hmap.containsKey(tokens.get(i).content)));
    }
    public void calculator(ArrayList<Token> exp)
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
                isTokenDigit = (token.getType()==Token.NUMBER)||(token.getType()==Token.PI)||(token.getType()==Token.E);

                isNextTokenDigit = nexttoken.getType() == Token.NUMBER;
                if((isTokenDigit && nexttoken.getType()==Token.LPAREN)
                        || (token.getType()==Token.RPAREN && isNextTokenDigit)
                        || (token.getType()==Token.RPAREN) && (nexttoken.getType()==Token.LPAREN)
                        || (token.getType()==Token.RPAREN && nexttoken.getType()==Token.POWER)
                        || (isTokenDigit && nexttoken.getType()==Token.SIN)
                        || (isTokenDigit && nexttoken.getType()==Token.COS)
                        || (isTokenDigit && nexttoken.getType()==Token.TAN)
                        || (token.getType()==Token.RPAREN && nexttoken.getType()==Token.TAN)
                        || (token.getType()==Token.RPAREN && nexttoken.getType()==Token.SIN)
                        || (token.getType()==Token.RPAREN && nexttoken.getType()==Token.COS)
                        )
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
        else if(token.getType()==Token.TIMES || token.getType()==Token.DIVIDE
                || token.getType()==Token.EQAULLESS|| token.getType()==Token.EQGREATER
                || token.getType()==Token.GREATER|| token.getType()==Token.LESS
                || token.getType()==Token.EQUAL|| token.getType()==Token.NOTEUAL)
        {
            result = 2;
        }
        else if(token.getType()==Token.PLUS||token.getType()==Token.MINUS
                || token.getType()==Token.AND|| token.getType()==Token.OR)
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

                if(token.getType()==Token.NUMBER||token.getType()==Token.PI||token.getType()==Token.E)
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
                if(token.getType()==Token.NUMBER||token.getType()==Token.PI||token.getType()==Token.E)
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
            double left = evalExpressionTree(eTree.getLeftSubtree());
            double right = evalExpressionTree(eTree.getRightSubtree());


            return evaluate(op, left, right);
        }
    }
 private double evaluate(Token op, double left,double right)
    {
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

            case Token.EQUAL : return (left==right)?1.0:0.0;
            case Token.OR : return (int)left | (int)right;
            case Token.AND : return (int)left & (int)right;
            case Token.NOTEUAL : return (left!=right)?1.0:0.0;
            case Token.GREATER : return (right>left)?1.0:0.0;
            case Token.LESS : return (right<left)?1.0:0.0;
            case Token.EQGREATER : return (right>=left)?1.0:0.0;
            case Token.EQAULLESS : return (right<=left)?1.0:0.0;
        }



        return result;
    }


}
