package com.example.user.formulacreator;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.Stack;

public class Calculate extends AppCompatActivity implements View.OnClickListener {
    int c;
    TextView formula;
    TextView result;
    Button calculate;
    Formula f;
    FormulaCreate fc;

    private Stack<Token> operatorStack;
    private Stack<Token> valueStack;
    private ArrayList<Token> tokens;
    private boolean error;
    private boolean work;

    double lastValue =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate2);

        fc = new FormulaCreate();
        operatorStack = new Stack<>();
        valueStack = new Stack<>();
        error = false;
        tokens = fc.getFormula().get(listView.p).getForm();


        formula = (TextView) findViewById(R.id.textView2);
        result = (TextView) findViewById(R.id.result);
        result.setText("0.0");
        formula.setText(fc.getFormula().get(listView.p).toString());
        calculate = (Button) findViewById(R.id.button);
        calculate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
            processInput();
                if(work==false)
                {
                    result.setText("Expression Error");
                }
                else
                {
                    result.setText(Double.toString(lastValue));
                }
                break;
        }
    }

    private void processOperator(Token t) {
        Token A = null, B = null;
        if (tokens.size()==0) {
            result.setText("Expression error.");
            error = true;
            return;
        } else {
            B = valueStack.pop();
        }
        if (valueStack.isEmpty()) {
            result.setText("Expression error.");
            error = true;
            return;
        } else {
            A = valueStack.pop();
        }
        Token R = t.operate(A.getValue(), B.getValue());
        valueStack.push(R);
    }

    public void processInput() {
        for (int n = 0; n < tokens.size(); n++) {
            final Token nextToken = tokens.get(n);
            if (nextToken.getType() == Token.NUMBER) {

                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Put value of variable for the formula");
                final EditText edit = new EditText(this);
                edit.setInputType(InputType.TYPE_CLASS_NUMBER);
                edit.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(edit);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        nextToken.putValue(Double.parseDouble(edit.getText().toString()));
                    }
                });
                alert.show();

                valueStack.push(nextToken);
            } else if (nextToken.getType() == Token.OPERATOR) {
                if (operatorStack.isEmpty() || nextToken.getPrecedence() > operatorStack.peek().getPrecedence()) {
                    operatorStack.push(nextToken);
                } else {
                    while (!operatorStack.isEmpty() && nextToken.getPrecedence() <= operatorStack.peek().getPrecedence()) {
                        Token toProcess = operatorStack.peek();
                        operatorStack.pop();
                        processOperator(toProcess);
                    }
                    operatorStack.push(nextToken);
                }
            } else if (nextToken.getType() == Token.LEFT_PARENTHESIS) {
                operatorStack.push(nextToken);
            } else if (nextToken.getType() == Token.RIGHT_PARENTHESIS) {
                while (!operatorStack.isEmpty() && operatorStack.peek().getType() == Token.OPERATOR) {
                    Token toProcess = operatorStack.peek();
                    operatorStack.pop();
                    processOperator(toProcess);
                }
                if (!operatorStack.isEmpty() && operatorStack.peek().getType() == Token.LEFT_PARENTHESIS) {
                    operatorStack.pop();
                } else {
                    System.out.println("Error: unbalanced parenthesis.");
                    error = true;
                }
            }
            else if(nextToken.getType()== Token.TRIG_NUMBER)
            {
                if(nextToken.content=="sin(var)")
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Put value of variable for sin");
                    final EditText edit = new EditText(this);
                    edit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    edit.setRawInputType(Configuration.KEYBOARD_12KEY);
                    alert.setView(edit);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            nextToken.putValue(Math.sin(Double.parseDouble(edit.getText().toString())));
                        }
                    });
                    alert.show();
                    valueStack.push(nextToken);

                }
                else if(nextToken.content=="cos(var)")
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Put value of variable for cos");
                    final EditText edit = new EditText(this);
                    edit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    edit.setRawInputType(Configuration.KEYBOARD_12KEY);
                    alert.setView(edit);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            nextToken.putValue(Math.cos(Double.parseDouble(edit.getText().toString())));
                        }
                    });
                    alert.show();
                    valueStack.push(nextToken);
                }
                else if(nextToken.content=="tan(var)")
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Put value of variable for tan");
                    final EditText edit = new EditText(this);
                    edit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    edit.setRawInputType(Configuration.KEYBOARD_12KEY);
                    alert.setView(edit);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            nextToken.putValue(Math.tan(Double.parseDouble(edit.getText().toString())));
                        }
                    });
                    alert.show();
                    valueStack.push(nextToken);
                }
            }

        }
        // Empty out the operator stack at the end of the input
        while (!operatorStack.isEmpty() && operatorStack.peek().getType() == Token.OPERATOR) {
            Token toProcess = operatorStack.peek();
            operatorStack.pop();
            processOperator(toProcess);
        }
        // Print the result if no error has been seen.
        if(error == false) {
            Token result = valueStack.peek();
            valueStack.pop();
            if (!operatorStack.isEmpty() || !valueStack.isEmpty()) {
                work = false;
            } else {
                work = true;
                lastValue = result. getValue();
            }
        }
    }




}
