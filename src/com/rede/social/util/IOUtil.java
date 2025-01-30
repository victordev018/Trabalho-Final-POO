package com.rede.social.util;

import java.util.Scanner;

public class IOUtil {

    private static Scanner in = new Scanner(System.in, "UTF-8").useDelimiter("\n");

    public IOUtil() {}

    public String getText(String message) {
        System.out.print(message);
        String result = in.next().trim();

        while (result.isEmpty() || result.matches("\\d+")){
            System.out.println("Você não escreveu algo válido. Por favor, escreva de novo.");
            result = in.next().trim();
        }

        return result;
    }

    public int getInt(String message) {
        System.out.print(message);
        while (!in.hasNextInt()){
            System.out.println("Sua entrada é inválida. Por favor,digite de novo");
            in.next();
            System.out.println(message);
        }
        int number = in.nextInt();
        in.nextLine();
        return number;
    }

    public int getIntSpecific(String message, int min, int max){
        int number = getInt(message);

        while (number < min || number > max) {
            System.out.println("Você deve fazer escolhas dentro dos limites");
            number = getInt(message);
        }

        return number;
    }

    public void showError(String error) {
        System.out.println(error);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void clearScreen() {
        System.out.print("Pressione <Enter> para continuar...");
        in.next();
        showMessage("\n".repeat(20));
    }

    public void closeScanner() {
        in.close();
    }
}
