package com.upgrad.tms.entities;

/**
 * This class represents Todo type tasks
 */
public class Todo extends Task {
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
