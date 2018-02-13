package com.round2.task2;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.lang.*;
import java.io.*;

class task2 {
    public static void main (String[] args) {
        Scanner val = new Scanner(System.in);
        while (true) {
            System.out.println("Желаете ли выполнить подсчёты?(Да/Нет)");
            switch (val.nextLine()) {
                case "Да":
                    String mathFunction = scanData();
                    Parser n = new Parser();
                    List<String> expression = n.reversePolandNotationParsing(mathFunction);
                    if (n.flagOfValidity && calculateData(expression)!=null)
                        System.out.println(calculateData(expression));
                    continue;
                case "Нет":
                    break;
                default:
                    System.out.println("Я вас не понимаю!(Да/Нет)");
                    continue;
            }
            break;
        }
    }

    private static String scanData() {
        Scanner dataScaner = new Scanner(System.in);
        System.out.println("Введите формулу");
        return dataScaner.nextLine();
    }

    public static BigDecimal calculateData(List<String> postfix) {
        Deque<BigDecimal> stack = new ArrayDeque<>();
        BigDecimal firstDiggit, secondDiggit;
        try {
            for (String x : postfix) {
                switch (x) {
                    case "^":
                        BigDecimal exponent = stack.pop();
                        stack.push(new BigDecimal(Math.pow(stack.pop().intValue(),(exponent.intValue()))));
                        break;
                    case "+":
                        stack.push(stack.pop().add(stack.pop()));
                        break;
                    case "-":
                        secondDiggit = stack.pop();
                        firstDiggit = stack.pop();
                        stack.push(firstDiggit.subtract(secondDiggit));
                        break;
                    case "*":
                        stack.push(stack.pop().multiply(stack.pop()));
                        break;
                    case "/":
                        secondDiggit = stack.pop();
                        firstDiggit = stack.pop();
                        if (secondDiggit.intValueExact()==0){
                            System.out.println("деление на 0 невозможно");
                        } else
                        stack.push(firstDiggit.divide(secondDiggit));
                        break;
                    case "u-":
                        stack.push(stack.pop().negate());
                        break;
                    default:

                        stack.push(new BigDecimal(x));
                        break;
                }
            }
        } catch (RuntimeException e){
            System.out.println("Невалидные данные");
        }
        if (stack.size() == 1) return stack.pop();
        else if (stack.size()>1){
            System.out.println("Число было разделено пробелом");
            return null;
        }
        else{
            return null;
        }

    }
}