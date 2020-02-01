package com.upgrad.tms.repository;

import com.upgrad.tms.entities.Assignee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AssigneeRepository {

    private Assignee[] assigneeList;
    private int nextEmptyIndex;

    private static AssigneeRepository assigneeRepository;

    private AssigneeRepository() throws IOException, ClassNotFoundException {
        assigneeList = new Assignee[10];
        nextEmptyIndex = 0;
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
            Object obj;
            //total objects should not be more than 10 as 10 is the maximum size
            while ((obj = oi.readObject()) != null) {
                assigneeList[nextEmptyIndex] = (Assignee) obj;
                nextEmptyIndex++;
                if (nextEmptyIndex > 10) {
                    throw new OutOfMemoryError("Array size exceeded");
                }
            }
            oi.close();
        }
        fi.close();
    }

    public Assignee getAssignee(String username) {
        for (int i = 0; i < nextEmptyIndex; i++) {
            if (assigneeList[i].getUsername().equals(username)) {
                return assigneeList[i];
            }
        }
        return null;
    }

    public Assignee saveAssignee(Assignee assignee) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("assignee.txt"));
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            assigneeList[nextEmptyIndex++] = assignee;
            for (int i = 0; i < assigneeList.length; i++) {
                outputStream.writeObject(assigneeList[i]);
            }
            //Adding null explicitly to mark close of objects
            outputStream.writeObject(null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return assignee;
    }

    public Assignee[] getAllAssignee() {
        Assignee[] assignees = new Assignee[nextEmptyIndex];
        for (int i = 0; i < nextEmptyIndex; i++) {
            assignees[i] = assigneeList[i];
        }
        return assignees;
    }
}
