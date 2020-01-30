package com.upgrad.tms.repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ProjectManagerRepository {

    private static ProjectManagerRepository managerRepository;
    private HashMap<String, String> managerCredentials;

    public static ProjectManagerRepository getInstance() {
        if (managerRepository == null) {
            managerRepository = new ProjectManagerRepository();
        }
        return managerRepository;
    }

    private ProjectManagerRepository() {
        initManagerCredentials();
    }

    private void initManagerCredentials() {
        managerCredentials = new HashMap<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("manager.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                managerCredentials.put(split[0], split[1]);
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found specific handling");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("General IO Exception handling");
            e.printStackTrace();
        }
    }

    public boolean isManager(String username) {
        return managerCredentials.containsKey(username);
    }

    public boolean isValidManagerCredentials(String username, String password) {
        return managerCredentials.containsKey(username) && managerCredentials.get(username).equals(password);
    }
}
