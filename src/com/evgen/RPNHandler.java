package com.evgen;

import java.util.ArrayDeque;

/**
 * Utility-класс. Хранит методы для перевода в обратную польскую запись, и вычисления обратной польской записи
 */
public final class RPNHandler {

    private RPNHandler(){
        throw new UnsupportedOperationException();
    }
    /**
     * Метод для перевода в обратную польскую запись
     */
    public static ArrayDeque<String> convertInfixToRPN(String[] infixArray){

        ArrayDeque<String> queue = new ArrayDeque<>();    // AD лучше, чем LinkedList
        ArrayDeque<String> stack = new ArrayDeque<>();    // AD лучше, чем Stack

        for (String token: infixArray) {
            if(LiteralType.getLiteralType(token) == LiteralType.OPERATION){     // если оператор
                if(token.equals("(")){                    // открывающую скобку просто в стек
                    stack.push(token);
                } else if(token.equals(")")){
                    while(!"(".equals(stack.peek())){  // при нахождении ), все операторы между скобками
                        queue.add(stack.pop());        // переносим в очередь из стека, а сами скобки выбрасываем
                    }
                    stack.pop();
                } else {               // если операция(не скобки)
                           // то пока стек не пуст, и приоритет операции меньше или равен приоритету из вершины стека -
                                  // переносим операторы в очередь
                    while (!stack.isEmpty() &&
                            Operator.getBySign(token).getPrecedence() <= Operator.getBySign(stack.peek()).getPrecedence()) {
                        queue.add(stack.pop());
                    }
                    stack.push(token);
                }

            } else if (LiteralType.getLiteralType(token) != LiteralType.UNKNOWN) { // если число, то просто кладём в очередь
                queue.add(token);
            }
        }

        while (!stack.isEmpty()){ // в конце, перекидываем в очередь, всё, что осталось в стеке
            queue.add(stack.pop());
        }
        return queue;
    }
    /**
     * Метод для вычисления выражения в обратной польской записи.
     * Умеет работать, в том числе, с выражениями типа 6 + VI
     *
     */
    public static String evalRPN(ArrayDeque<String> RPNExpression, LiteralType resultType) {
        String token;
        double result;
        double rightNum;
        double leftNum;
        double limitedVal; // TODO: убрать
        ArrayDeque<Double> stack = new ArrayDeque<>();

        while(!RPNExpression.isEmpty()){             // пока в выражении есть символы
            token = RPNExpression.pop();
            if(LiteralType.getLiteralType(token) == LiteralType.DECIMAL){

                limitedVal = Double.parseDouble(token);
                if (Main.LIMITED_FUNCTIONALITY && limitedVal > 10) { throw new IllegalArgumentException("Числа не должны быть больше 10!"); }

                stack.push(limitedVal);
            } else if(LiteralType.getLiteralType(token) == LiteralType.ROMAN){

                limitedVal = NumberConvertHandler.convertRomanToDecimal(token);
                if (Main.LIMITED_FUNCTIONALITY && limitedVal > 10) throw new IllegalArgumentException("Числа не должны быть больше 10!");

                stack.push(limitedVal);
            } else if (LiteralType.getLiteralType(token) == LiteralType.OPERATION){
                if(stack.size() >= 2) {
                    rightNum = stack.pop();
                    leftNum = stack.pop();
                    result = Operator.getBySign(token).eval(leftNum, rightNum);
                    //System.out.println(leftNum + " " + token + " " + rightNum + " = " + result); //debug
                    stack.push(result);
                } else {
                    throw new IllegalArgumentException("Лишний символ операции во входном массиве");
                }
            } else {
                throw new IllegalArgumentException("Неизвестный входной аргумент");
            }
        }
        result = stack.pop();
        if (resultType == LiteralType.ROMAN){
            if ((result > 3999) || (result < 1)) {
                return "Римскими цифрами не выразить сей ответ...\nAрабскими можно: " + result;
            } else {
                return NumberConvertHandler.convertDecimalToRoman((int)result); // тут будет срезана дробная часть
            }
        } else if (resultType == LiteralType.DECIMAL) {
            return String.valueOf(result);
        } else {
            throw new IllegalArgumentException("Недопустимый формат вывода результата");
        }
    }
}
