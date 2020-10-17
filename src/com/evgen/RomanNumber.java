package com.evgen;

import java.util.HashMap;
import java.util.Map;
/**
 * Перечисление Римских цифр, с эквивалетными значениями в десятичной СС
 * а так же метоы получения символьного представления и/или эквивалетного числа, через хешмапы, в тч
 */
public enum RomanNumber {
    I('I', 1),
    V('V', 5),
    X('X', 10),
    L('L', 50),
    C('C', 100),
    D('D', 500),
    M('M', 1000)
    ;
    private static final Map<Character, RomanNumber> BY_REPRESENT = new HashMap<>();
    private static final Map<Integer, RomanNumber> BY_DECIMAL = new HashMap<>();

    private final Character represent;
    private final Integer decimalValue;

    static {
        for (RomanNumber r : values()) {
            BY_REPRESENT.put(r.represent, r);
            BY_DECIMAL.put(r.decimalValue, r);
        }
    }

    RomanNumber(char represent, int decimalValue) {
        this.represent = represent;
        this.decimalValue = decimalValue;
    }

    public static RomanNumber getByRepresent(Character represent) {
        return BY_REPRESENT.get(represent);
    }
    public static RomanNumber getByDecimal(Integer decimalValue) {
        return BY_DECIMAL.get(decimalValue);
    }

    public int getDecimal() {
        return this.decimalValue;
    }

    public char getRepresent() {
        return this.represent;
    }

}

