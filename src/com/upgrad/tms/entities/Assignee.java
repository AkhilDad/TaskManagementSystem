package com.upgrad.tms.entities;

/**
 * This represents Assignee, which will do the task
 */

public class Assignee {
    //incremental user id given to the user
    private long id;
    //the name of the user
    private String name;
    //the username of the user, used to uniquely identify the user
    private String username;
    //the password of the user, this is needed for the login
    private String password;

    private Calendar<Task> taskCalendar;

    public Assignee(int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.taskCalendar = new Calendar<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Calendar<Task> getTaskCalendar() {
        return taskCalendar;
    }

    public void setTaskCalendar(Calendar<Task> taskCalendar) {
        this.taskCalendar = taskCalendar;
    }
}
