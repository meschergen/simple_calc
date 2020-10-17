package com.evgen;

import java.util.HashMap;
import java.util.Map;
/**
 * Перечисление доступных операций, с приоритетами, методами их получения, и самим вычислением.
 */
public enum Operator {
    PLUS("+", 1),
    MINUS("-", 1),
    DIVISION("/", 2),
    MULTIPLICATION("*", 2),
    OPEN_PARENTHESIS("(", 0),
    CLOSE_PARENTHESIS(")", 0)
    ;

    private static final Map<String, Operator> BY_SIGN = new HashMap<>();

    private final String sign;
    private final Integer precedence;

    static {
        for (Operator o: values()) {
            BY_SIGN.put(o.sign, o);
        }
    }

    Operator(String sign, int precedence) {
        this.sign = sign;
        this.precedence = precedence;
    }

    public static Operator getBySign(String sign) { return BY_SIGN.get(sign); }

    public int getPrecedence() { return this.precedence; }

    public double eval(double leftNumber, double rightNumber){
        switch (this.sign){
            case "+": return leftNumber + rightNumber;
            case "-": return leftNumber - rightNumber;
            case "/": return leftNumber / rightNumber;
            case "*": return leftNumber * rightNumber;
            default: throw new IllegalArgumentException("Недопустимый символ операции");
        }
    }

}

