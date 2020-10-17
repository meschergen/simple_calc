package com.evgen;
/**
 * Utility-класс. Хранит методы для перевода из римской в десятичную и обратно
 */
public final class NumberConvertHandler {

    private NumberConvertHandler(){
        throw new UnsupportedOperationException();
    }

    /**
     * Метод для перевода в Римскую СС из десятичной
     */
    public static int convertRomanToDecimal(String romanNum) {
                                                //regex для контроля корректности введёного римского числа
        String correctRomanNumberFormat = "(\\s*)(M{0,3})(CD|CM|D?C{0,3})(XL|XC|L?X{0,3})(IV|IX|V?I{0,3})(\\s*)";
        char currentChar;           //текущий римский символ
        char previousChar = 0;      // прошлое рс
        int previousDecimal;        // прошлое десятичное число
        int currentDecimal;         // текущее десятичное число

        int result = 0;

        if (!romanNum.matches(correctRomanNumberFormat)) {
            throw new IllegalArgumentException("Формат римского числа " + romanNum + " некорректен"); }

        for (int i = 0; i < romanNum.length(); i++) {
            currentChar = romanNum.charAt(romanNum.length() - i - 1);   // перебор справа налево
            currentDecimal = RomanNumber.getByRepresent(currentChar).getDecimal();     // получение эквивалента римского числа в десятичной сс

            if (currentChar == ' ') continue;    // пробелы игонрируем
            if (previousChar == 0) {            // если это первый попавшийся символ (нет прошлого символа)
                result += currentDecimal;       // то просто прибавляем его к результату
                previousChar = currentChar;     // переходим к следующему символу -> текущий будет прошлым
                continue;
            }
            previousDecimal = RomanNumber.getByRepresent(previousChar).getDecimal();
            previousChar = currentChar;

            if (currentDecimal < previousDecimal){  // если значение символа слева меньше, чем справа (прим IX)
                result -= currentDecimal;           // то вычитаем его (IX == 9 == 10 - 1)
            } else {
                result += currentDecimal;           // иначе - прибавляем (XI == 11 == 10 + 1)
            }
        }
        return result;
    }

    /**
     * Метод для перевода из десятичной СС в Римскую
     */
    public static String convertDecimalToRoman(int decimalNum) {

        int romanNumCounter;   // количество римских "единиц", от 1 до 9, с учётом разряда
        int numberRank;        // разрядное число (1000 / 100 / 10 / 1)

        if (decimalNum < 0 || decimalNum > 3999) { throw new IllegalArgumentException("Римское число не может быть > 3999"); }
        if (decimalNum == 0) return "0";

        StringBuilder result = new StringBuilder();
        int digitsCount = (int) Math.ceil(Math.log10(Math.abs(decimalNum) + 0.5));  //получаем кол-во разрядов числа

        for (int i = 0; i < digitsCount; i++){
            numberRank = (int) Math.pow(10,(digitsCount - 1 - i));

            romanNumCounter = decimalNum / numberRank;
            decimalNum -= (numberRank * romanNumCounter);

            if (romanNumCounter < 4) {    // от 1 до 3х штампуем "единицы" ( XX или III и тп)
                for (int j = 0; j < romanNumCounter; j++) {
                    result.append(RomanNumber.getByDecimal(numberRank).getRepresent());
                }
            } else if (romanNumCounter == 4) {              // ставим "единицу" и "пятёрку", с учётом разряда
                result.append(RomanNumber.getByDecimal(numberRank).getRepresent());
                result.append(RomanNumber.getByDecimal(numberRank * 5).getRepresent());
            } else if (romanNumCounter == 5 ) {             // "пятёрку", с учётом разряда
                result.append(RomanNumber.getByDecimal(numberRank * 5).getRepresent());
            } else if (romanNumCounter < 9 ) {              // 0т 6 до 8 пятёрка + единицы
                result.append(RomanNumber.getByDecimal(numberRank * 5).getRepresent());
                for (int j = 0; j < (romanNumCounter - 5); j++) {
                    result.append(RomanNumber.getByDecimal(numberRank).getRepresent());
                }
            } else if (romanNumCounter == 9 ) {              // единица и десять, с учётом разряда
                result.append(RomanNumber.getByDecimal(numberRank).getRepresent());
                result.append(RomanNumber.getByDecimal(numberRank * 10).getRepresent());
            } else {
                throw new ArithmeticException("Остаток от деления на эквивалентный разряд >= 10");
            }
        }
        return result.toString();
    }
}
