package com.evgen;

import java.util.regex.Pattern;

/**
 * Класс парсер выражений. Методы:
 * проверка на валидность введённого выражения и
 * разделение строки на литералы "3 + 5" -> ["3", "+", "5"]
 *
 */
public class ExpressionParser {

    private String expression;

    public ExpressionParser(String expression) {
        this.expression = expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public boolean isValidExpression() {
        if (this.expression.equals("EXIT")) {throw new IllegalArgumentException("Прервано пользователем");}
        if(Main.LIMITED_FUNCTIONALITY) {
            String validExpressionFormat = "(\\s*)[0-9IVX]{1,4}(\\s*)[+-/*](\\s*)[0-9IVX]{1,4}(\\s*)";
            return this.expression.matches(validExpressionFormat);
        } else {
            return true; // TODO: сделать нормальный regex, или убрать метод, в принципе, тк парсер должен справляться
        }
    }

    public String[] splitToInfix(){
        Pattern pattern = Pattern.compile("(?<=[-+/*()])|(?=[-+/*()])");
        return pattern.split(this.expression.replaceAll("\\s+", ""));
    }
}
