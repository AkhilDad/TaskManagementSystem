package com.upgrad.tms.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
        try (BufferedReader br = new BufferedReader(new FileReader("manager.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                managerCredentials.put(split[0], split[1]);
            }
        } catch (IOException e) {
            System.out.println("IO Exception handling");
            e.printStackTrace();
        }
    }

    public boolean isManager(String username) {
        return managerCredentials.containsKey(username);
    }

    public boolean isValidManagerCredentials(String username, String password) {
        return managerCredentials.containsKey(username) && managerCredentials.get(username).equals(password);
    }

    public void saveManager(String username, String password) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("manager.txt", true));
            String line = username+","+password;
            writer.newLine();
            writer.write(line);
            writer.close();
        } catch (IOException e) {
            System.out.println("IO Exception handling");
            e.printStackTrace();
        }
    }
}
