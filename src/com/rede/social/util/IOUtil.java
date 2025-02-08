package com.rede.social.util;

import com.rede.social.exception.global.InvalidInputError;

import java.util.Scanner;

public class IOUtil {

    private static Scanner in = new Scanner(System.in, "UTF-8").useDelimiter("\n");

    public IOUtil() {}

    public static String getText(String msg) {
        try {
            System.out.print(msg + " ");
            String input = in.nextLine().trim();
            if (input.matches(".*\\d.*")) {
                throw new InvalidInputError("Entrada inválida: apenas texto é permitido.");
            }
            return input;
        } catch (InvalidInputError e) {
            System.out.println(e.getMessage());
            return getText(msg);
        }
    }

    public static Integer getInt(String msg) {
        try {
            System.out.print(msg + " ");
            return Integer.parseInt(in.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Digite um número inteiro válido.");
            return getInt(msg);
        }
    }

    public static Integer getIntSpecific(String msg, int min, int max) {
        try {
            int number = getInt(msg);
            if (number < min || number > max) {
                throw new InvalidInputError("O número deve estar entre " + min + " e " + max + ".");
            }
            return number;
        } catch (InvalidInputError e) {
            System.out.println(e.getMessage());
            return getIntSpecific(msg, min, max);
        }
    }

    public static void showError(String error) {
        System.out.println(error);
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }

    public static void clearScreen() {
        System.out.print("Pressione <Enter> para continuar...");
        in.nextLine();
        showMessage("\n".repeat(20));
    }

    public static void closeScanner() {
        in.close();
    }
}