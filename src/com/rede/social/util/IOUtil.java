package com.rede.social.util;

import java.util.Scanner;

public class IOUtil {

    private static final Scanner in = new Scanner(System.in, "UTF-8").useDelimiter("\n");

    public IOUtil() {}

    public String getText(String message) {
        System.out.print(colorUtils.YELLOW + message + colorUtils.RESET + " " + colorUtils.WHITE);
        String result = in.next().trim();
        System.out.print(colorUtils.RESET); // Resetando cor após entrada do usuário

        while (result.isEmpty() || result.matches("\\d+")) {
            System.out.println(colorUtils.RED + "Você não escreveu algo válido. Por favor, escreva de novo." + colorUtils.RESET);
            System.out.print(colorUtils.YELLOW + message + colorUtils.RESET + " " + colorUtils.WHITE);
            result = in.next().trim();
            System.out.print(colorUtils.RESET);
        }

        return result;
    }

    public int getInt(String message) {
        System.out.print(colorUtils.YELLOW + message + colorUtils.RESET + " " + colorUtils.WHITE);
        while (!in.hasNextInt()) {
            System.out.println(colorUtils.RED + "Sua entrada é inválida. Por favor, digite de novo." + colorUtils.RESET);
            in.next();
            System.out.print(colorUtils.YELLOW + message + colorUtils.RESET + " " + colorUtils.WHITE);
        }
        int number = in.nextInt();
        in.nextLine();
        System.out.print(colorUtils.RESET);
        return number;
    }

    public int getIntSpecific(String message, int min, int max) {
        int number = getInt(message);

        while (number < min || number > max) {
            System.out.println(colorUtils.RED + "Você deve fazer escolhas dentro dos limites." + colorUtils.RESET);
            number = getInt(message);
        }

        return number;
    }

    public void showError(String error) {
        System.out.println(colorUtils.RED + error + colorUtils.RESET);
    }

    public void showMessage(String message) {
        System.out.println(colorUtils.CYAN + message + colorUtils.RESET);
    }

    public void showWarning(String warning) {
        System.out.println(colorUtils.YELLOW + warning + colorUtils.RESET);
    }

    public void clearScreen() {
        System.out.print(colorUtils.YELLOW + "Pressione <Enter> para continuar..." + colorUtils.RESET);
        in.next();
        showMessage("\n".repeat(20));
    }

    public void closeScanner() {
        in.close();
    }
}
