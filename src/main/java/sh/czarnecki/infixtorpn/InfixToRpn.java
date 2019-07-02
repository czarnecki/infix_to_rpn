package sh.czarnecki.infixtorpn;

import java.util.Stack;

public class InfixToRpn {

    private static Stack<Character> charStack = new Stack<>();

    public static String infToRpn(String term) {
        var upn = new StringBuilder();
        for (int index = 0; index < term.length(); index++) {
            getNextDigits(index, term, upn);
            getNextOperandOrParen(index, term, upn);
            closeParenthesis(index, term, upn);
        }
        clearRemainingStack(upn);
        return upn.toString();
    }

    private static void getNextDigits(int index, String term, StringBuilder upn) {
        if (Character.isDigit(term.charAt(index))) {
            var prevIndex = index;
            while (index < term.length() && Character.isDigit(term.charAt(index))) {
                index += 1;
            }
            upn.append(term, prevIndex, index).append(" ");
        }
    }

    private static void getNextOperandOrParen(int index, String term, StringBuilder upn) {
        if (index < term.length() && isAnyOperandOrOpenParen(term.charAt(index))) {
            if (isPlusOrMinus(term.charAt(index))) {
                if (!charStack.empty() && isAnyOperand(charStack.peek())) {
                    upn.append(charStack.pop()).append(" ");
                }
            }
            if (isMulOrDiv(term.charAt(index))) {
                if (!charStack.empty() && isMulOrDiv(charStack.peek())) {
                    upn.append(charStack.pop()).append(" ");
                }
            }
            charStack.push(term.charAt(index));
        }
    }

    private static void closeParenthesis(int index, String term, StringBuilder upn) {
        if (index < term.length() && term.charAt(index) == ')') {
            while (!charStack.empty()) {
                if (charStack.peek() == '(') {
                    charStack.pop();
                    break;
                } else {
                    upn.append(charStack.pop()).append(" ");
                }
            }
        }
    }

    private static void clearRemainingStack(StringBuilder upn) {
        while (!charStack.empty()) {
            upn.append(charStack.pop()).append(" ");
        }
    }

    private static boolean isAnyOperandOrOpenParen(char c) {
        return "+-/*(".contains(Character.toString(c));
    }

    private static boolean isAnyOperand(char c) {
        return "*/+-".contains(Character.toString(c));
    }

    private static boolean isMulOrDiv(char c) {
        return "*/".contains(Character.toString(c));
    }

    private static boolean isPlusOrMinus(char c) {
        return "+-".contains(Character.toString(c));
    }
}
