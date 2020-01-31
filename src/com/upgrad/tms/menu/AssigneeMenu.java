package com.upgrad.tms.menu;

import com.upgrad.tms.entities.Assignee;
import com.upgrad.tms.entities.Task;
import com.upgrad.tms.repository.AssigneeRepository;
import com.upgrad.tms.util.DateUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
                seeTodayTasks();
                showTopOptions();
                break;
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

    private void seeTodayTasks() {
        if (MainMenu.loggedInUsername != null) {
            Assignee assignee = assigneeRepository.getAssignee(MainMenu.loggedInUsername);
            List<Task> taskList = assignee.getTaskCalendar().getTaskList();
            List<Task> todayTaskList = new ArrayList<>();
            for (Task task : taskList) {
                if (DateUtils.isSameDate(task.getDueDate(), Calendar.getInstance().getTime())) {
                    todayTaskList.add(task);
                }
            }
            if (todayTaskList.isEmpty()) {
                System.out.println("Hurray! No task for today");
            } else {
                printTaskList(todayTaskList);
            }
        }
    }

    private void seeAllTasks() {
        if (MainMenu.loggedInUsername != null) {
            Assignee assignee = assigneeRepository.getAssignee(MainMenu.loggedInUsername);
            List<Task> taskList = assignee.getTaskCalendar().getTaskList();
            printTaskList(taskList);
        }
    }

    private void printTaskList(List<Task> taskList) {
        for (Task task : taskList) {
            task.printTaskOnConsole();
            System.out.println("\n");
        }
    }
}
