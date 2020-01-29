package com.upgrad.tms.menu;

import java.util.Scanner;

public class SubOrdinateMenu implements OptionsMenu {

    public void showTopOptions() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. View assigned task");
        System.out.println("2. Mark task complete");
        System.out.println("3. Send report to manager");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
//                acceptUserCreation();
//                break;
            case 2:
                //show all task for today
            case 3:
                //Assign task to users
        }

    }


}
