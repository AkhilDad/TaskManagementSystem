package com.upgrad.tms.menu;

import com.upgrad.tms.entities.Assignee;
import com.upgrad.tms.repository.AssigneeRepository;
import com.upgrad.tms.repository.ProjectManagerRepository;

import java.io.IOException;
import java.util.Scanner;

public class MainMenu {
    public static String loggedInUsername;
    private ProjectManagerRepository managerRepository;
    private AssigneeRepository assigneeRepository;

    public MainMenu() throws IOException, ClassNotFoundException {
        managerRepository = ProjectManagerRepository.getInstance();
        assigneeRepository = AssigneeRepository.getInstance();
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

    public static void exit() {
        MainMenu.loggedInUsername = null;
        System.exit(0);
    }

    private void showMenu(OptionsMenuType optionsMenuType) {
        try {
            MenuFactory.getMenuByType(optionsMenuType).showTopOptions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this function will take care of what do be done with username and password
    private void processInput(String username, String password) {
        if (managerRepository.isManager(username)) {
            if (managerRepository.isValidManagerCredentials(username, password)) {
                MainMenu.loggedInUsername = username;
                showMenu(OptionsMenuType.PROJECT_MANAGER);
            } else {
                System.out.println("username and password do not match for username: " + username);
                getLoginDetails();
            }
        } else {
            Assignee assignee = assigneeRepository.getAssignee(username);
            if (assignee == null) {
                System.out.println("user not found with user name: " + username);
                getLoginDetails();
            } else if (!assignee.getPassword().equals(password)) {
                System.out.println("username and password do not match for username: " + username);
                getLoginDetails();
            } else {
                MainMenu.loggedInUsername = username;
                showMenu(OptionsMenuType.ASSIGNEE);
            }
        }
    }
}
