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
    public static final int EQUAL = 18;
    public static final int GREATER = 19;
    public static final int LESS = 20;
    public static final int EQGREATER = 21;
    public static final int EQAULLESS = 22;
    public static final int NOTEUAL = 23;
    public static final int IF = 24;
    public static final int ELSE = 25;
    public static final int THEN = 26;
    public static final int AND = 27;
    public static final int OR = 28;



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
            case "pi":
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
            case "==":
                type = EQUAL;
                content = contents;
                break;
            case "!=":
                type = NOTEUAL;
                content = contents;
                break;
            case "<":
                type = GREATER;
                content = contents;
                break;
            case ">":
                type = LESS;
                content = contents;
                break;
            case "<=":
                type = EQGREATER;
                content = contents;
                break;
            case ">=":
                type = EQAULLESS;
                content = contents;
                break;
            case "IF":
                type = IF;
                content = contents;
                break;
            case "ELSE":
                type = ELSE;
                content = contents;
                break;
            case "THEN":
                type = THEN;
                content = contents;
                break;
            case "&&":
                type = AND;
                content = contents;
                break;
            case "||":
                type = OR;
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
        if(type==PLUS||type==MINUS||type==TIMES||type==DIVIDE||type==LPAREN||type==RPAREN
                ||type==SIN||type==LOG||type==EQUAL||type==NOTEUAL||type==GREATER||type==LESS
                ||type==EQAULLESS||type==EQGREATER||type==AND||type==OR)
            return true;
        return false;
    }
    int getType() { return type; }
    double getValue() { return value; }


}

