package com.upgrad.tms.menu;

import com.upgrad.tms.entities.Assignee;
import com.upgrad.tms.repository.AssigneeRepository;
import com.upgrad.tms.repository.ProjectManagerRepository;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ManagerMenu implements OptionsMenu {

    private AssigneeRepository assigneeRepository;

    private ProjectManagerRepository managerRepository;

    public ManagerMenu() throws IOException, ClassNotFoundException {
        assigneeRepository = AssigneeRepository.getInstance();
        managerRepository = ProjectManagerRepository.getInstance();
    }

    public void showTopOptions() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Create new user");
        System.out.println("2. Display all users");
        System.out.println("3. Create another manager");
        System.out.println("4. Exit");
        int choice = 0; //Invalid default value just to satisfy compiler, this will never reach switch
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Wrong input type, In input only numbers are allowed");
            showTopOptions();
        }
        switch (choice) {
            case 1:
                acceptAssigneeCreation();
                System.out.println("User created successfully");
                showTopOptions();
                break;
            case 2:
                displayAllAssignees();
                showTopOptions();
                break;
            case 3:
                createNewManager();
                showTopOptions();
                break;
            case 4:
                System.exit(0);
                break;
            default:
                wrongInput();
        }
    }

    private void createNewManager() {
        Scanner sc = new Scanner(System.in);
        String username = getUserName(sc);
        System.out.println("Enter password");
        String password = sc.nextLine();
        managerRepository.saveManager(username, password);
    }

    private void acceptAssigneeCreation() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name");
        String name = sc.nextLine();
        String username = getUserName(sc);
        System.out.println("Enter password");
        String password = sc.nextLine();
        Assignee assignee = new Assignee(assigneeRepository.getAllAssignee().size() + 1, name, username, password);
        assigneeRepository.saveAssignee(assignee);
    }

    private String getUserName(Scanner sc) {
        String finalUserName = null;
        do {
            System.out.println("Enter username");
            String username = sc.nextLine();
            Assignee existingAssignee = assigneeRepository.getAssignee(username);
            if (existingAssignee != null || managerRepository.isManager(username)) {
                System.out.println("Username already exists, Enter some other username");
            } else {
                finalUserName = username;
            }
        } while(finalUserName == null);
        return finalUserName;
    }

    private void displayAllAssignees() {
        List<Assignee> allAssignees = assigneeRepository.getAllAssignee();
        allAssignees.forEach(assignee -> {
            System.out.println("Name: "+ assignee.getName()+" UserName: "+ assignee.getUsername());
        });
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
