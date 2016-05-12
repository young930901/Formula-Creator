package com.example.user.formulacreator;

import java.io.Serializable;

/**
 * Created by user on 4/28/2016.
 */
public class Token {
    public static final int MINUS = 4;
    public static final int NUMBER = 9;
    public static final int PLUS = 3;
    public static final int RPAREN = 8;
    public static final int TIMES = 5;
    public static final int LPAREN = 7;
    public static final int SIN = 10;
    public static final int COS = 11;
    public static final int TAN = 12;
    public static final int DIVIDE = 13;
    public static final int PI = 14;
    public static final int E = 15;
    public static final int POWER = 16;
    public static final int UNKNOWN = -1;
    public static final int LOG = 17;


    private int type;
    private double value;
    public String operator;
    public String content;

    public Token()
    {
        type = UNKNOWN;
    }
    public Token(String contents) {
        content = contents;
        switch(contents) {
            case "+":
                type = PLUS;
                operator = contents;
                break;
            case "-":
                type = MINUS;
                operator = contents;
                break;
            case "*":
                type = TIMES;
                operator = contents;
                break;
            case "/":
                type = DIVIDE;
                operator = contents;
                break;
            case "(":
                type = LPAREN;
                break;
            case ")":
                type = RPAREN;
                break;
            case "sin":
                type = SIN;
                operator = contents;
                break;
            case "cos":
                type = COS;
                operator = contents;
                break;
            case "tan":
                type = TAN;
                operator = contents;
                break;
            case "Pi":
                type = PI;
                content = contents;
                value = 3.14;
                break;
            case "e":
                type = E;
                content = contents;
                value = 2.17;
                break;
            case "^":
                type = POWER;
                content = contents;
                break;
            case "log":
                type = LOG;
                content = contents;
                break;
            default:
                type = NUMBER;

        }
    }

    public Token(double x) {
        type = NUMBER;
        value = x;
    }
    public void putValue(double v)
    {
        value = v;
    }
    public boolean isOp()
    {
        if(type==PLUS||type==MINUS||type==TIMES||type==DIVIDE||type==LPAREN||type==RPAREN||type==SIN||type==LOG)
            return true;
        return false;
    }
    int getType() { return type; }
    double getValue() { return value; }


}

