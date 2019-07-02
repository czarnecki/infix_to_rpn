package sh.czarnecki.infixtorpn;

import java.util.Stack;

public class InfixToRpn {

    /**
     * Transforms a term in infix notation to reverse polish notation
     *
     * The String is transformed with the shunting-yard algorithm by Edsger
     * Dijkstra.
     * If the given term is an invalid mathematical expression the result is
     * undefined.
     * Works only for the operands "+", "-", "*", "/" and with parenthesises.
     * Parenthesises can be nested.
     * The numbers may only be natural numbers including zero.
     *
     * @param term Term in infix notation that is to be transformed
     * @return Returns a String in reverse polish notation
     */
    public static String infToRpn(String term) {
        term = term.replaceAll("\\s", "");
        var operandStack = new Stack<Character>();
        var rpn = new StringBuilder();
        for (int index = 0; index < term.length(); index++) {
            addNextDigit(index, term, rpn);
            getNextOperandOrParen(index, term, rpn, operandStack);
            closeParenthesis(index, term, rpn, operandStack);
        }
        clearRemainingStack(rpn, operandStack);
        return rpn.toString();
    }

    /**
     * Adds the next digit to rpn
     *
     * @param index Current index
     * @param term Term to be transformed
     * @param rpn Term in reverse polish notation
     */
    private static void addNextDigit(int index, String term, StringBuilder rpn) {
        if (Character.isDigit(term.charAt(index))) {
            var prevIndex = index;
            while (index < term.length() && Character.isDigit(term.charAt(index))) {
                index += 1;
            }
            rpn.append(term, prevIndex, index).append(" ");
        }
    }

    /**
     * Add the next operand to the stack and append old to rpn if existent
     *
     * @param index Current index
     * @param term Term to be transformed
     * @param rpn Term in reverse polish notation
     * @param operandStack Stack containing operands
     */
    private static void getNextOperandOrParen(final int index, String term, StringBuilder rpn, Stack<Character> operandStack) {
        if (index < term.length() && isAnyOperandOrOpenParen(term.charAt(index))) {
            if (isPlusOrMinus(term.charAt(index))) {
                if (!operandStack.empty() && isAnyOperand(operandStack.peek())) {
                    rpn.append(operandStack.pop()).append(" ");
                }
            }
            if (isMulOrDiv(term.charAt(index))) {
                if (!operandStack.empty() && isMulOrDiv(operandStack.peek())) {
                    rpn.append(operandStack.pop()).append(" ");
                }
            }
            operandStack.push(term.charAt(index));
        }
    }

    /**
     * Closes current parenthesis and starts next if nested
     *
     * @param index Current index
     * @param term Term to be transformed
     * @param rpn Term in reverse polish notation
     * @param operandStack Stack containing operands
     */
    private static void closeParenthesis(final int index, String term, StringBuilder rpn, Stack<Character> operandStack) {
        if (index < term.length() && term.charAt(index) == ')') {
            while (!operandStack.empty()) {
                if (operandStack.peek() == '(') {
                    operandStack.pop();
                    break;
                } else {
                    rpn.append(operandStack.pop()).append(" ");
                }
            }
        }
    }

    /**
     * Finishes transformation up by appending remaining operands
     *
     * @param rpn Term in reverse polish notation
     * @param operandStack Stack containing operands
     */
    private static void clearRemainingStack(StringBuilder rpn, Stack<Character> operandStack) {
        while (!operandStack.empty()) {
            rpn.append(operandStack.pop()).append(" ");
        }
    }

    /**
     * Checks if char is an operand or an opening parenthesis
     * Checked operands are "+", "-", "/", and "*".
     * It is also checked for "("
     *
     * @param c char to be checked
     * @return Returns true if char is one of the checked operands else false
     */
    private static boolean isAnyOperandOrOpenParen(char c) {
        return "+-/*(".contains(Character.toString(c));
    }

    /**
     * Same as @{isAnyOperandOrOpenParen} but without check for opening parenthesis
     *
     * @param c Char to be checked
     * @return Returns true if char is one of the checked operands else false
     */
    private static boolean isAnyOperand(char c) {
        return "*/+-".contains(Character.toString(c));
    }

    /**
     * Checks if char is a "*" or "/"
     *
     * @param c Char to be checked
     * @return Returns true of char is "*" or "/" else false
     */
    private static boolean isMulOrDiv(char c) {
        return "*/".contains(Character.toString(c));
    }

    /**
     * Checks if char is a "+" or "-"
     *
     * @param c Char to be checked
     * @return Returns true of char is "+" or "-" else false
     */
    private static boolean isPlusOrMinus(char c) {
        return "+-".contains(Character.toString(c));
    }
}
