package com.rede.social.util;

import com.rede.social.exception.global.InvalidInputError;

import java.util.Scanner;

public class IOUtil {

    private static final Scanner in = new Scanner(System.in, "UTF-8").useDelimiter("\n");

    public IOUtil() {}

    public String getText(String message) {
        try {
            System.out.print(ColorUtils.YELLOW + message + ColorUtils.RESET + " " + ColorUtils.WHITE);
            String input = in.nextLine().trim();
            if (input.matches(".*\\d.*")) {
                throw new InvalidInputError("Entrada inválida: apenas texto é permitido.");
            }
            return input;
        } catch (InvalidInputError e) {
            System.out.println(e.getMessage());
            return getText(message);
        }
    }
  
    public Integer getInt(String message) {
        try {
            System.out.print(ColorUtils.YELLOW + message + ColorUtils.RESET + " " + ColorUtils.WHITE);
            return Integer.parseInt(in.nextLine().trim());
        } catch (NumberFormatException e) {
            showError("Digite um número inteiro válido.");
            return getInt(message);
        }
    }

    public Integer getIntSpecific(String msg, int min, int max) {
        try {
            int number = getInt(msg);
            if (number < min || number > max) {
                throw new InvalidInputError("O número deve estar entre " + min + " e " + max + ".");
            }
            return number;
        } catch (InvalidInputError e) {
            showError(e.getMessage());
            return getIntSpecific(msg, min, max);
        }
    }

    public void showError(String error) {
        System.out.println(ColorUtils.RED + error + ColorUtils.RESET);
    }

    public void showMessage(String message) {
        System.out.println(ColorUtils.CYAN + message + ColorUtils.RESET);
    }

    public void showWarning(String warning) {
        System.out.println(ColorUtils.YELLOW + warning + ColorUtils.RESET);
    }

    public void clearScreen() {
        System.out.print(ColorUtils.YELLOW + "Pressione <Enter> para continuar..." + ColorUtils.RESET);
        in.nextLine();
        showMessage("\n".repeat(20));
    }

    public void closeScanner() {
        in.close();
    }
}