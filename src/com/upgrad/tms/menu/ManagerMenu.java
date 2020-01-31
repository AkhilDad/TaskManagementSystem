package com.upgrad.tms.menu;

import com.upgrad.tms.entities.Assignee;
import com.upgrad.tms.entities.Meeting;
import com.upgrad.tms.entities.Task;
import com.upgrad.tms.entities.TaskStatus;
import com.upgrad.tms.entities.Todo;
import com.upgrad.tms.repository.AssigneeRepository;
import com.upgrad.tms.repository.ProjectManagerRepository;
import com.upgrad.tms.util.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
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
        System.out.println("4. Create task and assign");
        System.out.println("5. Exit");
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
                System.exit(0);
                break;
            default:
                wrongInput();
        }
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
        Date dueDate = getDateFromUser(sc);
        task.setTitle(title);
        task.setPriority(priority);
        task.setDueDate(dueDate);
        task.setStatus(TaskStatus.PENDING);
    }

    private Date getDateFromUser(Scanner sc) {
        Date formattedDate = null;
        do {
            System.out.println("Enter due date ["+DateUtils.DateFormat.DAY_MONTH_YEAR_HOUR_MIN_SLASH_SEPARATED +"]: ");
            String dueDateString = sc.nextLine();
            try {
                formattedDate = DateUtils.getFormattedDate(dueDateString, DateUtils.DateFormat.DAY_MONTH_YEAR_HOUR_MIN_SLASH_SEPARATED);
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

    private void wrongInput() {
        System.out.println("Entered wrong choice, input again");
        showTopOptions();
    }

    private void showAgain() {
        System.out.println("Functionality under implementation");
        showTopOptions();
    }

}
