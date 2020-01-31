package com.upgrad.tms.menu;

import com.upgrad.tms.entities.Assignee;
import com.upgrad.tms.entities.Meeting;
import com.upgrad.tms.entities.Task;
import com.upgrad.tms.entities.TaskStatus;
import com.upgrad.tms.entities.Todo;
import com.upgrad.tms.repository.AssigneeRepository;
import com.upgrad.tms.repository.ProjectManagerRepository;
import com.upgrad.tms.util.DateUtils;
import javafx.util.Pair;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.PriorityQueue;
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
        System.out.println("4. Create task and assign");
        System.out.println("5. Get all assignees who have a task on the given date");
        System.out.println("6. Get all tasks based on priority");
        System.out.println("7. Exit");
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
                createTaskAndAssign();
                showTopOptions();
                break;
            case 5:
                getAssigneesForSpecificDate();
                showTopOptions();
                break;
            case 6:
                getAllTaskBasedOnPriority();
                showTopOptions();
                break;
            case 7:
                MainMenu.exit();
                break;
            default:
                wrongInput();
        }
    }

    private void getAllTaskBasedOnPriority() {
        PriorityQueue<Pair<Task, String>> priorityQueue = assigneeRepository.getAllTaskAssigneePairByPriority();
        //We can't use iterator in priority queue
        //ref:https://stackoverflow.com/questions/8129122/how-to-iterate-over-a-priorityqueue
        while (!priorityQueue.isEmpty()) {
            Pair<Task, String> pair = priorityQueue.poll();
            Task task = pair.getKey();
            System.out.println("Task priority: "+ task.getPriority()
                    + " Title: "+task.getTitle()
                    + " User: "+pair.getValue());
        }
    }

    private void getAssigneesForSpecificDate() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the due date for which you want to get all the users who have pending tasks");
        Date dueDateForPendingUser = getDateFromUser(sc, DateUtils.DateFormat.DAY_MONTH_YEAR_SLASH_SEPARATED);
        Collection<Assignee> assignees = assigneeRepository.getAllAssigneeForDueDate(dueDateForPendingUser);
        if (assignees.isEmpty()) {
            System.out.println("No assignees are there for the given due date");
        }
        assignees.forEach(assignee -> {
            System.out.println("Id: " + assignee.getId() + " Name: " + assignee.getName() + " UserName: " + assignee.getUsername() + " Total tasks: " + assignee.getTaskCalendar().getTaskList().size());
        });
    }

    private void createTaskAndAssign() {
        Scanner sc = new Scanner(System.in);
        Task task;
        int taskChoice = 0;
        while (!(taskChoice == 1 || taskChoice == 2)) {
            System.out.println("Enter task type\n1. Todo\n2. Meeting");
            taskChoice = sc.nextInt();
        }
       if (taskChoice == 1) {
           task = createTodo();
       } else {
           task = createMeeting();
       }
       Assignee assignee = getAssigneeForTask(sc);
       task.setId((long)assignee.getTaskCalendar().getTaskList().size() + 1);
       assignee.getTaskCalendar().add(task);
       assigneeRepository.updateListToFile();
    }

    private Assignee getAssigneeForTask(Scanner sc) {
        Assignee assignee = null;
        do {
            System.out.println("Enter username whom to assign task");
            String username = sc.next();
            assignee = assigneeRepository.getAssignee(username);
            if (assignee == null) {
                System.out.println("Assignee not found for the username: "+username);
            }
        } while (assignee == null);
       return assignee;
    }

    private Task createMeeting() {
        Scanner sc = new Scanner(System.in);
        Meeting meeting = new Meeting();
        fillTaskValues(sc, meeting);
        System.out.print("Enter meeting agenda: ");
        meeting.setAgenda(sc.nextLine());
        System.out.print("Enter location: ");
        meeting.setLocation(sc.nextLine());
        System.out.print("Enter meeting url: ");
        meeting.setUrl(sc.nextLine());
        return meeting;
    }

    private void fillTaskValues(Scanner sc, Task task) {
        System.out.print("Enter title of the task: ");
        String title = sc.nextLine();
        System.out.println("Enter priority of the task [High-Low] [1-5]: ");
        int priority = sc.nextInt();
        //Just to read \n from the previous nextInt() reading
        sc.nextLine();
        Date dueDate = getDateFromUser(sc, DateUtils.DateFormat.DAY_MONTH_YEAR_HOUR_MIN_SLASH_SEPARATED);
        task.setTitle(title);
        task.setPriority(priority);
        task.setDueDate(dueDate);
        task.setStatus(TaskStatus.PENDING);
    }

    private Date getDateFromUser(Scanner sc, String dateFormat) {
        Date formattedDate = null;
        do {
            System.out.println("Enter due date ["+ dateFormat+"]: ");
            String dueDateString = sc.nextLine();
            try {
                formattedDate = DateUtils.getFormattedDate(dueDateString, dateFormat);
            } catch (ParseException e) {
                System.out.println("Wrong date format, Please enter correct date");
            }
        } while (formattedDate == null);
        return formattedDate;
    }

    private Task createTodo() {
        Scanner sc = new Scanner(System.in);
        Todo todo = new Todo();
        fillTaskValues(sc, todo);
        System.out.print("Enter description: ");
        todo.setDescription(sc.nextLine());
        return todo;
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
        if (allAssignees.isEmpty()) {
            System.out.println("No assignees has been added");
            return;
        }
        allAssignees.forEach(assignee -> {
            System.out.println("Id: "+assignee.getId()+" Name: "+ assignee.getName()+" UserName: "+ assignee.getUsername()+" Total tasks: "+assignee.getTaskCalendar().getTaskList().size());
        });
    }
}
