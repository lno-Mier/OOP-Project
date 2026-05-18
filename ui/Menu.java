package ui;

import java.util.Scanner;

import services.Database;

public abstract class Menu {
    protected final Scanner scanner;
    protected final Database db;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
        this.db = Database.getInstance();
    }

    public abstract void show();

    protected String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    protected int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Input a valid number.");
            }
        }
    }

    protected double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Input a valid number.");
            }
        }
    }

    protected void printDivider() {
        System.out.println("  " + "-".repeat(40));
    }

    protected void printHeader(String title) {
        System.out.println();
        System.out.println("  " + "=".repeat(40));
        System.out.println("  " + title);
        System.out.println("  " + "=".repeat(40));
    }
}
