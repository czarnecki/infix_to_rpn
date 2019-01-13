package infixtorpn;

import java.util.Stack;
import java.util.Scanner;

public class InfixToRpn {
    
    static Stack<Character> characterStack = new Stack<Character>();
    static Scanner scanInput = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.print("Term: ");
            String userInput  = scanInput.nextLine();
            userInput = userInput.replaceAll("\\s", "");
            if (userInput.matches("([-+]?[0-9]*\\.?[0-9]+[\\/\\+\\-\\*])+([-+]?[0-9]*\\.?[0-9]+)")) {
                System.out.println(infToRpn(userInput));
            }
        }
    }

    static String infToRpn(String ifterm) {
        String upnterm = "";
        int currentIndex = 0;
        int previousIndex = 0;
        while (currentIndex < ifterm.length()) {
            if (ifterm.charAt(currentIndex) >= '0' && ifterm.charAt(currentIndex) <= '9') {
                previousIndex = currentIndex;
                while (currentIndex < ifterm.length() && ifterm.charAt(currentIndex) >= '0' && ifterm.charAt(currentIndex) <= '9') {
                    currentIndex += 1;
                }
                upnterm += ifterm.substring(previousIndex, currentIndex) + " ";
            }
            if (currentIndex < ifterm.length() && "+-/*(".contains(Character.toString(ifterm.charAt(currentIndex)))) {
                if ("+-".contains(Character.toString(ifterm.charAt(currentIndex)))) {
                    if(!characterStack.empty() && "*/+-".contains(Character.toString(characterStack.peek()))) {
                        upnterm += characterStack.pop() + " ";
                    }
                }
                if ("*/".contains(Character.toString(ifterm.charAt(currentIndex)))) {
                    if (!characterStack.empty() && "*/".contains(Character.toString(characterStack.peek()))) {
                        upnterm += characterStack.pop();
                    }
                }
                characterStack.push(ifterm.charAt(currentIndex));
            }
            if (currentIndex < ifterm.length() && ifterm.charAt(currentIndex) == ')') {
                while (!characterStack.empty()) {
                    if (characterStack.peek() == '(') {
                        characterStack.pop();
                        break;
                    } else {
                        upnterm += characterStack.pop() + " ";
                    }
                }
            }
            currentIndex += 1;
        }
        while(!characterStack.empty()) {
            upnterm += characterStack.pop() + " ";
        }
        return upnterm;
    }
}
