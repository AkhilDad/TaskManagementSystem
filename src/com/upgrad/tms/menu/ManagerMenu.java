package com.upgrad.tms.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ManagerMenu implements OptionsMenu {

    public ManagerMenu() {
    }

    public void showTopOptions() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Create new user");
        System.out.println("2. Display all users");
        System.out.println("3. Exit");
        int choice = 0; //Invalid default value just to satisfy compiler, this will never reach switch
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Wrong input type, In input only numbers are allowed");
            showTopOptions();
        }
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
