package com.upgrad.tms.menu;

import com.upgrad.tms.entities.Assignee;
import com.upgrad.tms.entities.Task;
import com.upgrad.tms.repository.AssigneeRepository;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AssigneeMenu implements OptionsMenu {

    private AssigneeRepository assigneeRepository;

    public AssigneeMenu() throws IOException, ClassNotFoundException {
        assigneeRepository = AssigneeRepository.getInstance();
    }

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
                seeAllTasks();
                showTopOptions();
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                showAgain();
                break;
            case 6:
                MainMenu.exit();
                break;
            default:
                wrongInput();
        }
    }

    private void seeAllTasks() {
        if (MainMenu.loggedInUsername != null) {
            Assignee assignee = assigneeRepository.getAssignee(MainMenu.loggedInUsername);
            List<Task> taskList = assignee.getTaskCalendar().getTaskList();
            for (Task task : taskList) {
                task.printTaskOnConsole();
                System.out.println("\n");
            }
        }
    }
}
