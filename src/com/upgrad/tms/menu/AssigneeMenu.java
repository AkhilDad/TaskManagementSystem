package com.upgrad.tms.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AssigneeMenu implements OptionsMenu {

    public void showTopOptions() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. See all tasks");
        System.out.println("2. See Today's Task");
        System.out.println("3. See Task sorted on priority");
        System.out.println("4. Tasks by task category");
        System.out.println("5. Change task status");
        System.out.println("6. Exit");
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
            case 3:
            case 4:
            case 5:
                showAgain();
                break;
            case 6:
                System.exit(0);
                break;
            default:
                wrongInput();
        }
    }


}
