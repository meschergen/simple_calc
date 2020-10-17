package com.evgen;

/**
 * Перечисление типов литералов, с методами получения типа для конкретного литерала
 * и методом получения единого, для массива
 */
public enum LiteralType{
    ROMAN("Римские числа"),
    DECIMAL("Десятичные числа"),
    OPERATION("Операция"),
    UNKNOWN("Неизвестый тип")
    ;

    private final String description;

    LiteralType(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return this.description;
    }

    public static LiteralType getLiteralType(String lit){
        String validDecimalFormat;
        String validRomanFormat;
        String validOperationFormat;
        if(Main.LIMITED_FUNCTIONALITY) {
            validDecimalFormat ="[0-9]{1,2}";
            validRomanFormat ="[IVX]{1,4}";
            validOperationFormat ="[-*/+]";
        } else {
            validDecimalFormat ="[0-9]+";
            validRomanFormat ="[IVXLCDM]+";
            validOperationFormat ="[-*/+()]";
        }

        if (lit.matches(validDecimalFormat)) {
            return LiteralType.DECIMAL;
        } else if (lit.matches(validRomanFormat)) {
            return LiteralType.ROMAN;
        } else if (lit.matches(validOperationFormat)) {
            return LiteralType.OPERATION;
        } else {
            return LiteralType.UNKNOWN;
        }
    }

    public static LiteralType getCompatibleType(String[] literals){
        LiteralType numType = LiteralType.UNKNOWN;
        for (int i = 0; i < literals.length; i++) {
            //System.out.println(literals[i]); // debug
            if (i == 0) {
                numType = getLiteralType(literals[i]);
                if(numType == LiteralType.OPERATION) { numType = getLiteralType(literals[i + 1]); }
            } else {
                if (getLiteralType(literals[i]) != LiteralType.OPERATION && getLiteralType(literals[i]) != numType) {
                    throw new IllegalArgumentException("Числа несовместимы!");
                }
            }
        }
        if (numType == LiteralType.UNKNOWN){ throw new IllegalArgumentException("Входные данные некорректны!"); }
        return numType;
    }
}
