package com.upgrad.tms.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the tasks for a particular assignee
 * @param <T>
 */
public class Calendar<T> {

    private List<T>  taskList = new ArrayList<>();

    public List<T> getTaskList() {
        return taskList;
    }

    public void add(T task) {
        taskList.add(task);
    }
}
