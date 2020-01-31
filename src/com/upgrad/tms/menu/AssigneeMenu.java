package com.upgrad.tms.menu;

import com.upgrad.tms.entities.Assignee;
import com.upgrad.tms.entities.Meeting;
import com.upgrad.tms.entities.Task;
import com.upgrad.tms.entities.Todo;
import com.upgrad.tms.exception.NotFoundException;
import com.upgrad.tms.repository.AssigneeRepository;
import com.upgrad.tms.util.DateUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
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
                seeTaskSortedOnPriority();
                showTopOptions();
                break;
            case 4:
                seeTaskByCategory();
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

    private void seeTaskByCategory() {
        Map<String, List<Task>> listMap = new HashMap<>();
        List<Class<? extends Task>> classTypes = List.of(Meeting.class, Todo.class);
        //init list for all classes there in classTypes
        for (Class<? extends Task> classType : classTypes) {
            listMap.put(classType.getSimpleName(), new ArrayList<>());
        }

        Assignee assignee = assigneeRepository.getAssignee(MainMenu.loggedInUsername);
        List<Task> taskList = assignee.getTaskCalendar().getTaskList();
        for (Task task : taskList) {
            List<Task> taskTypeList = listMap.get(task.getClass().getSimpleName());
            if (taskTypeList != null) {
                taskTypeList.add(task);
            } else {
                throw new NotFoundException("Task type not found");
            }
        }

        for (Map.Entry<String, List<Task>> listEntry : listMap.entrySet()) {
            System.out.println("======= Category: " + listEntry.getKey() + " =======");
            for (Task task : listEntry.getValue()) {
                task.printTaskOnConsole();
            }
            System.out.println("=======================");
        }
    }


    private void seeTaskSortedOnPriority() {
        if (MainMenu.loggedInUsername != null) {
            Assignee assignee = assigneeRepository.getAssignee(MainMenu.loggedInUsername);
            List<Task> taskList = assignee.getTaskCalendar().getTaskList();
            PriorityQueue<Task> taskPriorityQueue = new PriorityQueue<>(new Comparator<Task>() {
                @Override
                public int compare(Task t1, Task t2) {
                    return t1.getPriority() - t2.getPriority();
                }
            });
            for (Task task : taskList) {
                if (DateUtils.isSameDate(task.getDueDate(), Calendar.getInstance().getTime())) {
                    taskPriorityQueue.add(task);
                }
            }
            while (!taskPriorityQueue.isEmpty()) {
                taskPriorityQueue.poll().printTaskOnConsole();
            }
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
