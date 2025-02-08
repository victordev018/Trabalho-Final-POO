package com.rede.social.util;

public class colorUtils {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[38;2;255;255;85m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static void printColor(String format, Object... args) {
        System.out.printf(BLUE + format + RESET, args);
    }

    public static void titleColor(){
        System.out.println(PURPLE + "███████╗██╗     ██╗  ██╗   ██╗ █████╗ ██████╗ ██████╗ \n" +
                "██╔════╝██║     ██║  ╚██╗ ██╔╝██╔══██╗██╔══██╗██╔══██╗\n" +
                "█████╗  ██║     ██║   ╚████╔╝ ███████║██████╔╝██████╔╝\n" +
                "██╔══╝  ██║     ██║    ╚██╔╝  ██╔══██║██╔═══╝ ██╔═══╝ \n" +
                "███████╗███████╗███████╗██║   ██║  ██║██║     ██║     \n" +
                "╚══════╝╚══════╝╚══════╝╚═╝   ╚═╝  ╚═╝╚═╝     ╚═╝     \n" +
                "                                                      " + RESET);
    }

}
