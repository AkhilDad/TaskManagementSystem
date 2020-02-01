package com.upgrad.tms.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProjectManagerRepository {

    private static ProjectManagerRepository managerRepository;
    private String usernameList[];
    private String passwordList[];
    private int nextEmptyIndex;

    public static ProjectManagerRepository getInstance() {
        if (managerRepository == null) {
            managerRepository = new ProjectManagerRepository();
        }
        return managerRepository;
    }

    private ProjectManagerRepository() {
        nextEmptyIndex = 0;
        initManagerCredentials();
    }

    private void initManagerCredentials() {
        usernameList = new String[10];
        passwordList = new String[10];
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("manager.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                usernameList[nextEmptyIndex] = split[0];
                passwordList[nextEmptyIndex] = split[1];
                nextEmptyIndex++;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("IO Exception handling");
            e.printStackTrace();
        }
    }

    public boolean isManager(String username) {
        boolean isUsernameExists = false;
        for (int i = 0; i < nextEmptyIndex; i++) {
            isUsernameExists = usernameList[i].equals(username);
            if (isUsernameExists) {
                break;
            }
        }
        return isUsernameExists;
    }

    public boolean isValidManagerCredentials(String username, String password) {
        boolean isValidCredentials = false;
        for (int i = 0; i < nextEmptyIndex; i++) {
            isValidCredentials = usernameList[i].equals(username) && passwordList[i].equals(password);
            if (isValidCredentials) {
                break;
            }
        }
        return isValidCredentials;
    }
}
