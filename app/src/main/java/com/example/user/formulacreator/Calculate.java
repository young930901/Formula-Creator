package com.example.user.formulacreator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayDeque;
import java.util.Stack;

public class Calculate extends AppCompatActivity implements View.OnClickListener {
    int c;
    TextView formula;
    TextView askValue;
    EditText askNum;
    Button enter;
    Formula f;
    FormulaCreate fc;
    int position = listView.p;
    private Stack<Token> operatorStack;
    private Stack<Double> operandStack;
    private ArrayDeque<Token> postfixDeque;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate2);


        fc = new FormulaCreate();
        formula = (TextView) findViewById(R.id.textView2);
        askValue = (TextView) findViewById(R.id.textView4);
        askNum = (EditText) findViewById(R.id.editText);
        formula.setText(fc.getFormula().get(listView.p).toString());

        enter = (Button) findViewById(R.id.button);
        enter.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                break;
        }
    }

    private void processOperator(Token op) {
        if (operatorStack.empty()) {
            operatorStack.push(op);
        } else {
            Token topOp = operatorStack.peek();
            if (op.getType() == Token.LEFT_PARENTHESIS) {
                operatorStack.push(op);
            } else if ((op.getType() == Token.LEFT_PARENTHESIS || op.getPrecedence() > topOp.getPrecedence()) && op.getType() != Token.RIGHT_PARENTHESIS) {
                operatorStack.push(op);
            } else if (op.getType() != Token.RIGHT_PARENTHESIS) {
                while (topOp.getType() != Token.LEFT_PARENTHESIS) {
                    operatorStack.pop();
                    postfixDeque.add(topOp);
                    if (!operatorStack.empty()) {
                        topOp = operatorStack.peek();
                    }
                }
                operatorStack.pop(); // removes the right parenthesis
            } else {
                // Pop all stacked operators with equal
                // or higher precedence than op.
                while (!operatorStack.empty() && op.getPrecedence() <= op.getPrecedence() && topOp.getType() != Token.LEFT_PARENTHESIS) {
                    operatorStack.pop();
                    postfixDeque.add(topOp);
                    if (!operatorStack.empty()) {
                        // Reset topOp.
                        topOp = operatorStack.peek();
                    }
                }
                operatorStack.push(op);
            }
        }
    }

    public double evaluate(ArrayDeque<Token> postfix) {
        double result = 0.0;
        double first = 0.0;
        double second = 0.0;

        operandStack = new Stack<Double>();
        while (!postfixDeque.isEmpty()) {
            Token c = postfix.poll();

            if (c.getType() == Token.NUMBER) {
                //ask for the number
                
                operandStack.push(Double.parseDouble(c.getValue()));
            } else if (c.content == "Pi") {
                operandStack.push(3.14);
            } else if (c.content == "e") {
                operandStack.push(2.17);
            } else if (c.operator == "+") {
                first = operandStack.pop();
                second = operandStack.pop();
                result = first + second;
                operandStack.push(result);
            } else if (c.operator == "-") {
                first = operandStack.pop();
                second = operandStack.pop();
                result = second - first;
                operandStack.push(result);
            } else if (c.operator == "/") {
                first = operandStack.pop();
                second = operandStack.pop();
                result = second / first;
                operandStack.push(result);
            } else if (c.operator == "*") {
                first = operandStack.pop();
                second = operandStack.pop();
                result = first * second;
                operandStack.push(result);
            } else if (c.operator == "^") {
                first = operandStack.pop();
                second = operandStack.pop();
                result = Math.pow(second, first);
                operandStack.push(result);
            } else if (c.operator == "!") {
                first = operandStack.pop();
                result = factorial(first);
                operandStack.push(result);
            } else if (c.operator == "sin") {
                first = operandStack.pop();
                result = Math.sin(first);
                operandStack.push(result);
            }
        }
        return operandStack.pop();
    }

    private double factorial(double f) {
        // TODO: if f is of the form x.0, convert to int and do !
        if (f % 1 == 0) {
            int count = (int) f;
            while (count != 2) {
                count--;
                f *= count;
            }
            return f;
        }

        return 1;
    }
}
