package com.upgrad.tms.menu;

import com.upgrad.tms.repository.ProjectManagerRepository;

import java.util.Scanner;

public class MainMenu {
    private ProjectManagerRepository managerRepository;

    public MainMenu() {
        managerRepository = ProjectManagerRepository.getInstance();
    }

    public void show() {
        getLoginDetails();
    }

    private void getLoginDetails() {
        //Scanner object to take input from keyboard.
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = sc.nextLine();
        System.out.println("Enter password:");
        String password = sc.nextLine();
        processInput(username, password);
    }

    //this function will take care of what do be done with username and password
    private void processInput(String username, String password) {
        if (managerRepository.isManager(username)) {
            if (managerRepository.isValidManagerCredentials(username, password)) {
                showMenu(OptionsMenuType.PROJECT_MANAGER);
            } else {
                System.out.println("username and password do not match for username: " + username);
                getLoginDetails();
            }
        } else {
            System.out.println("user not found with user name: " + username);
            getLoginDetails();
        }
    }

    private void showMenu(OptionsMenuType optionsMenuType) {
        try {
            MenuFactory.getMenuByType(optionsMenuType).showTopOptions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
