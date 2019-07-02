package sh.czarnecki;

import java.util.Scanner;

import static sh.czarnecki.infixtorpn.InfixToRpn.infToRpn;

public class Main {
    public static void main(String[] args) {
        System.out.print("Term: ");
        var scanner = new Scanner(System.in);
        var input = scanner.nextLine();
        input = input.replaceAll("\\s", "");
        System.out.println(infToRpn(input));
    }
}
