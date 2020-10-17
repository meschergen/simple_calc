package com.evgen;

import java.util.ArrayDeque;
import java.util.Scanner;

/**
 * Калькулятор. работает с целыми римскими/арабскими цифрами.
 * Вычисление производится через перевод входной строки в обратную польскую запись и её вычисление
 * Поддерживаются операции [ + / * - ] и работа со скобками.
 * Унарный минус и работа с отрицательными числами, не реализованы. Однако, ответ может быть отрицательным.
 * Доступен перевод из десятичной в римскую и обратно, вплоть до 3999 с контролем корректности введённого числа. (Никаких IXV и тп.)
 * При невозможности выразить ответ римскими - выводит арабскими, с сообщением.
 * По умолчанию, ограничен ТЗ. (Условиями на значение, от 0 до 10, и форматом "1 + 2")
 * Снять ограничение можно выставив значение LIMITED_FUNCTIONALITY = false.
 */

public class Main {

    public static final boolean LIMITED_FUNCTIONALITY = true; // TODO: Вырезать эту переменную/её использование и сами "ограничения"

    public static void main(String[] args) {

        String input;           // ввод пользователя
        String output;          // вывод результата вычислений
        String[] literals;      // разбитая на литералы входная строка
        LiteralType numberType; // Римские /Арабские цифры
        ExpressionParser ep;    // парсер введённого выражения
        ArrayDeque<String> expressionInRPN;     // введённое выражение, в виде обратной польской записи

        Scanner in = new Scanner(System.in);

        boolean keepCalculating = true;
        boolean isValid;

        System.out.println("Приложение Калькулятор v0.3\n Доступные операции [+/*-] вида '1 + 2' ");

        while(keepCalculating) {
            System.out.print("Введите операцию: ");
            do {
                input = in.nextLine();
                ep = new ExpressionParser(input);
                isValid = ep.isValidExpression();

                if(!isValid) {System.out.println("Некорректный вид операции! Повторите ввод!(или введите EXIT, для выхода)");}
            } while(!isValid);       // пока ввод некорректен

            //input = "(13 - 4 * 8 + 24 ) / (1 + 2 * 4 - 3)"; // = 0.83
            // input = "(3+15-8)/(4+2*3)-6"; // = -5
            //input = "";
            //input = "V * XII";
            //input = "10 + 5 * 2"; // =20

            ep = new ExpressionParser(input);

            literals = ep.splitToInfix();        // разбиение строки на литералы. Инфиксная форма

            numberType = LiteralType.getCompatibleType(literals);         // Римские / Арабские цифры

            expressionInRPN = RPNHandler.convertInfixToRPN(literals);     // перевод выражения в обратную польскую запись (RPN)

            output = RPNHandler.evalRPN(expressionInRPN, numberType);     // вычисление RPN
            System.out.println(input + " = " + output);       //вывод выражения с ответом

            System.out.println("Повторить? (Да/...)");
            if(!in.nextLine().equalsIgnoreCase("да")) {
                keepCalculating = false;
                System.out.println("Всего хорошего!");
            }
        }
    }
}
