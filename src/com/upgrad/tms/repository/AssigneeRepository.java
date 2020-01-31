package com.upgrad.tms.repository;

import com.upgrad.tms.entities.Assignee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssigneeRepository {

    private Map<String, Assignee> usernameAssigneeMap;
    private List<Assignee> assigneeList;

    private static AssigneeRepository assigneeRepository;

    private AssigneeRepository() throws IOException, ClassNotFoundException {
        initSubOrdinates();
    }

    public static AssigneeRepository getInstance() throws IOException, ClassNotFoundException {
        if (assigneeRepository == null) {
            assigneeRepository = new AssigneeRepository();
        }
        return assigneeRepository;
    }

    private void initSubOrdinates() throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream(new File("assignee.txt"));
        if (fi.available() > 0) {
            ObjectInputStream oi = new ObjectInputStream(fi);
            assigneeList = (List<Assignee>) oi.readObject();
            oi.close();
        } else {
            assigneeList = new ArrayList<>();
        }
        usernameAssigneeMap = new HashMap<>();
        for (Assignee assignee : assigneeList) {
            usernameAssigneeMap.put(assignee.getUsername(), assignee);
        }
        fi.close();
    }

    public Assignee getAssignee(String username) {
        return usernameAssigneeMap.get(username);
    }

    public Assignee saveAssignee(Assignee assignee) {
        assigneeList.add(assignee);
        usernameAssigneeMap.put(assignee.getUsername(), assignee);
        updateListToFile();
        return assignee;
    }

    public List<Assignee> getAllAssignee() {
        return assigneeList;
    }

    public void updateListToFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("assignee.txt"));
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(assigneeList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
