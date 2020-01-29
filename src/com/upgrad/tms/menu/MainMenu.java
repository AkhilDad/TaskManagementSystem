package com.upgrad.tms.menu;

import com.upgrad.tms.entities.User;
import com.upgrad.tms.entities.UserRole;
import com.upgrad.tms.repository.UserRepository;

import java.io.IOException;
import java.util.Scanner;

public class MainMenu {

    private UserRepository userRepository;

    public MainMenu() throws IOException, ClassNotFoundException {
        userRepository = UserRepository.getInstance();
    }

    public void show() {
        getLoginDetails();
    }

    private void getLoginDetails() {
        //Scanner object to take input from keyboard.
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = sc.next();
        System.out.println("Enter password:");
        String password = sc.next();
        processInput(username, password);
    }

    //this function will take care of what do be done with username and password
    private void processInput(String username, String password) {
        if (userRepository.isManager(username)) {
            if (userRepository.isValidManagerCredentials(username, password)) {
                showMenu(UserRole.MANAGER);
            } else {
                System.out.println("user name and password do not match for username: " + username);
                getLoginDetails();
            }
        } else {
            User user = userRepository.getUser(username);
            if (user == null) {
                System.out.println("user not found with user name: " + username);
                getLoginDetails();
            } else if (!user.getPassword().equals(password)) {
                System.out.println("user name and password do not match for username: " + username);
                getLoginDetails();
            } else {
                showMenu(user.getUserRole());
            }
        }
    }

    private void showMenu(UserRole userRole) {
        try {
            UserMenuFactory.getUserSpecificMenu(userRole).showTopOptions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
