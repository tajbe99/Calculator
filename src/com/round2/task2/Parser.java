package com.round2.task2;

import java.util.*;

public class Parser {
        private static String operators = "+-*/^";
        private static String delimiters = "() " + operators;
        public static boolean flagOfValidity = true;

        private static boolean isDelimiter(String token) {
            if (token.length() != 1) return false;
            for (int i = 0; i < delimiters.length(); i++) {
                if (token.charAt(0) == delimiters.charAt(i)) return true;
            }
            return false;
        }

        private static boolean isOperator(String token) {
            if (token.equals("u-")) return true;
            for (int i = 0; i < operators.length(); i++) {
                if (token.charAt(0) == operators.charAt(i)) return true;
            }
            return false;
        }

        private static int priority(String token) {
            if (token.equals("(")) return 1;
            if (token.equals("+") || token.equals("-")) return 2;
            if (token.equals("*") || token.equals("/")) return 3;
            if (token.equals("^")) return 4;
            return 5;
        }

        public static List<String> reversePolandNotationParsing(String mathFunction) {
            List<String> postfix = new ArrayList<>();
            Deque<String> stack = new ArrayDeque<>();
            StringTokenizer tokenizer = new StringTokenizer(mathFunction.replace(",","."),
                    delimiters,
                    true);
            String prev = "";
            String curr = "";
            while (tokenizer.hasMoreTokens()) {
                curr = tokenizer.nextToken();
                if (!tokenizer.hasMoreTokens() && isOperator(curr)) {
                    System.out.println("Отсутсвие числа после оператора");
                    flagOfValidity =false;
                    return postfix;
                }
                if (curr.equals(" ")) continue;
                else if (isDelimiter(curr)) {
                    if (curr.equals("("))
                        stack.push(curr);
                    else if (curr.equals(")")) {
                        while (!(stack.peek().equals("("))) {
                            postfix.add(stack.pop());
                            if (stack.isEmpty()) {
                                System.out.println("Отсутствие закрывающей скобки");
                                flagOfValidity =false;
                                return postfix;
                            }
                        }
                        stack.pop();
                    }
                    else {
                        if (curr.equals("-") && (prev.equals("") || (isDelimiter(prev)  && !(prev.equals(")"))))) {
                            curr = "u-";
                        }
                        else {
                            while (!stack.isEmpty() && (priority(curr) <= priority(stack.peek()))) {
                                postfix.add(stack.pop());
                            }
                        }
                        stack.push(curr);
                    }
                }

                else {
                    postfix.add(curr);
                }
                prev = curr;
            }

            while (!stack.isEmpty()) {
                if (isOperator(stack.peek())) postfix.add(stack.pop());
                else {
                    System.out.println("Отсутсвие закрывающей скобки");
                    flagOfValidity =false;
                    return postfix;
                }
            }
            return postfix;
        }
    }
