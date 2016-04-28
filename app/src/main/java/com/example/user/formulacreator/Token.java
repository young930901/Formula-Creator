package com.example.user.formulacreator;

/**
 * Created by user on 4/28/2016.
 */
public class Token {
    public static final int UNKNOWN = -1;
    public static final int NUMBER = 0;
    public static final int OPERATOR = 1;
    public static final int LEFT_PARENTHESIS = 2;
    public static final int RIGHT_PARENTHESIS = 3;
    public static final int FIXED_VALUE =4;
    public static final int TRIG = 5;
    public static final int FACRORIAL = 6;




    private int type;
    private double value;
    public String operator;
    private int precedence;
    public String content;
    public Token() {
        type = UNKNOWN;
    }

    public Token(String contents) {
        content = contents;
        switch(contents) {
            case "+":
                type = OPERATOR;
                operator = contents;
                precedence = 1;
                break;
            case "-":
                type = OPERATOR;
                operator = contents;
                precedence = 1;
                break;
            case "*":
                type = OPERATOR;
                operator = contents;
                precedence = 2;
                break;
            case "/":
                type = OPERATOR;
                operator = contents;
                precedence = 2;
                break;
            case "(":
                type = LEFT_PARENTHESIS;
                precedence = 3;
                break;
            case ")":
                type = RIGHT_PARENTHESIS;
                precedence = 3;
                break;
            case "sin":
                type = TRIG;
                operator = contents;
                precedence = 3;
                break;
            case "cos":
                type = TRIG;
                operator = contents;
                precedence = 3;
                break;
            case "tan":
                type = TRIG;
                operator = contents;
                precedence = 3;
                break;
            case "!":
                type = FACRORIAL;
                operator = contents;
                precedence = 2;
                break;
            case "P":
                type = OPERATOR;
                operator = contents;
                precedence = 2;
                break;
            case "C":
                type = OPERATOR;
                operator = contents;
                precedence = 2;
                break;
            case "Sum":
                type = OPERATOR;
                operator = contents;
                precedence = 2;
                break;
            case "log":
                type = OPERATOR;
                operator = contents;
                precedence = 2;
                break;
            case "Pi":
                type = FIXED_VALUE;
                content = contents;
                value = 3.14;
                break;
            case "e":
                type = FIXED_VALUE;
                content = contents;
                value = 2.17;
                break;
            default:
                type = NUMBER;
                try {
                    value = Double.parseDouble(contents);
                } catch (Exception ex) {
                    type = UNKNOWN;
                }
        }
    }

    public Token(double x) {
        type = NUMBER;
        value = x;
    }

    int getType() { return type; }
    double getValue() { return value; }
    int getPrecedence() { return precedence; }

    Token operate(double a,double b) {
        double result = 0;
        switch(operator) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
        }
        return new Token(result);
    }
}

