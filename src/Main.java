import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static Map<String, Integer> romanToArabic = new HashMap<>();
    static int result = 0;
    static int left = 0;
    static int right = 0;
    static boolean isRoman = false;

    public static void main(String[] args) throws Exception {

        romanToArabic.put("I", 1);
        romanToArabic.put("II", 2);
        romanToArabic.put("III", 3);
        romanToArabic.put("IV", 4);
        romanToArabic.put("V", 5);
        romanToArabic.put("VI", 6);
        romanToArabic.put("VII", 7);
        romanToArabic.put("VIII", 8);
        romanToArabic.put("IX", 9);
        romanToArabic.put("X", 10);
        System.out.println("Введите матиметическое выражение в формате \na + b  a - b  a * b  a / b");
        System.out.println("Можно использовать римские и арабские числа\n от 1 до 10 включительно, не более");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        System.out.println(calc(scanner.nextLine()));

    }


    public static String calc(String input) throws Exception {
        String[] splited = input.split(" ");
        if (splited.length < 3) {
            throw new Exception("строка не является математической операцией");
        }
        if (splited.length > 3) {
            throw new Exception("формат математической операции не удовлетворяет заданию\n" +
                    "- два операнда и один оператор (+, -, /, *)\n");
        }
        convertAndInitOperands(splited);
        if ((left < 1 || left > 10) || (right < 1 || right > 10)) {
            throw new Exception("Используйте чиста от 1 до 10 включительно, не более");
        }
        switch (splited[1]) {
            case "+" -> {
                if (isRoman) {
                    return intToRoman(left + right);
                }
                result = left + right;
            }
            case "-" -> {
                if (isRoman && left < right) {
                    throw new Exception("В римской системе нет отрицательных чисел");
                }
                if (isRoman) {
                    return intToRoman(left - right);
                }
                result = left - right;
            }
            case "/" -> {
                if (isRoman) {
                    return intToRoman(left / right);
                }
                result = left / right;
            }
            case "*" -> {
                if (isRoman) {
                    return intToRoman(left * right);
                }
                result = left * right;
            }
            default -> {
                throw new Exception("Unknown Operator");
            }
        }
        return String.valueOf(result);
    }

    private static void convertAndInitOperands(String[] splited) throws Exception {
        if (romanToArabic.containsKey(splited[0]) && romanToArabic.containsKey(splited[2])) {
            left = romanToArabic.get(splited[0]);
            right = romanToArabic.get(splited[2]);
            isRoman = true;
        } else if (romanToArabic.containsKey(splited[0]) || romanToArabic.containsKey(splited[2])) {
            throw new Exception("используются одновременно разные системы счисления");
        } else {
            left = Integer.parseInt(splited[0]);
            right = Integer.parseInt(splited[2]);
            isRoman = false;
        }
    }

    private static String intToRoman(int num) {
        if (num == 0) {
            return "nulla";
        }
        String[] keys = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] vals = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder roman = new StringBuilder();
        int ind = 0;

        while (ind < keys.length) {
            while (num >= vals[ind]) {
                var d = num / vals[ind];
                num = num % vals[ind];
                roman.append(keys[ind].repeat(d));
            }
            ind++;
        }
        return roman.toString();
    }

}