package com.upgrad.tms.menu;

import com.upgrad.tms.entities.User;
import com.upgrad.tms.entities.UserRole;
import com.upgrad.tms.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ManagerMenu implements OptionsMenu {

    private UserRepository userRepository;

    public ManagerMenu() throws IOException, ClassNotFoundException {
        this.userRepository = UserRepository.getInstance();
    }

    public void showTopOptions() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Create new user");
        System.out.println("2. Display all users");
        System.out.println("3. Get user role by username");
        System.out.println("4. Create and assign task");
        System.out.println("5. See tasks due on today");

        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                acceptUserCreation();
                break;
            case 2:
                displayAllUsers();
                break;
            case 3:
            case 4:
            case 5:
                showAgain();
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

    private void displayAllUsers() {
        List<User> allUsers = userRepository.getAllUsers();
        allUsers.forEach( user -> {
            System.out.println("Name: "+user.getName()+ "Role: "+user.getUserRole()+" UserName: "+user.getUsername());
        });
    }

    private void acceptUserCreation() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name");
        String name = sc.next();
        System.out.println("Enter username");
        String username = sc.next();
        System.out.println("Enter password");
        String password = sc.next();
        System.out.println("Enter role (MANAGER, SUB_ORDINATE)");
        String role = sc.next();
        UserRole userRole = UserRole.valueOf(role);
        User user = new User(userRepository.getAllUsers().size() + 1, name, username, password, userRole);
        userRepository.saveUser(user);
    }

    private void getUserRole(String username) {
        userRepository.getUserRoleByUsername(username);
    }
}
