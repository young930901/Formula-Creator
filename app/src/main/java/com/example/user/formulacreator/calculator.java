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
public class calculator {

    private Stack<Token> operatorStack;
    private Stack<Token> valueStack;
    private ArrayList<Token> tokens;
    private boolean error;

    public calculator(Formula f) {
        operatorStack = new Stack<>();
        valueStack = new Stack<>();
        error = false;
        tokens = f.getForm();

    }

    private void processOperator(Token t) {
        Token A = null, B = null;
        if (tokens.size()==0) {
            System.out.println("Expression error.");
            error = true;
            return;
        } else {
            B = valueStack.pop();
        }
        if (valueStack.isEmpty()) {
            System.out.println("Expression error.");
            error = true;
            return;
        } else {
            A = valueStack.pop();
        }
        Token R = t.operate(A.getValue(), B.getValue());
        valueStack.push(R);
    }

    public void processInput(String input) {

        // Main loop - process all input tokens
        for (int n = 0; n < tokens.size(); n++) {
            Token nextToken = tokens.get(n);
            if (nextToken.getType() == Token.NUMBER) {


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
                System.out.println("Expression error.");
            } else {
                System.out.println("The result is " + result.getValue());
            }
        }
    }

}
