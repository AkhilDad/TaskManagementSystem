package com.upgrad.tms.menu;

import java.util.Scanner;

public class ManagerMenu implements OptionsMenu {

    public ManagerMenu() {
    }

    public void showTopOptions() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Create new user");
        System.out.println("2. Display all users");
        System.out.println("3. Exit");

        int choice = sc.nextInt();
        switch (choice) {
            case 1:
            case 2:
                showAgain();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                wrongInput();
        }

    }

    private void wrongInput() {
        System.out.println("Entered wrong choice, input again");
        showTopOptions();
    }

    private void showAgain() {
        System.out.println("Functionality under implementation");
        showTopOptions();
    }

}
