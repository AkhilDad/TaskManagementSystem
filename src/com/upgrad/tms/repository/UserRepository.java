package com.upgrad.tms.repository;

import com.upgrad.tms.entities.User;
import com.upgrad.tms.entities.UserRole;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserRepository {

    private Map<String, User> usernameUserMap;
    private List<User> userList;
    private Map<String, String> managerCredentials;

    private static UserRepository userRepository;

    private UserRepository() throws IOException, ClassNotFoundException {
        initSubOrdinates();
        initManagerCredentials();
    }

    //use of buffered reader
    private void initManagerCredentials() {
        managerCredentials = new HashMap<>();
        try {
            FileInputStream fStream = new FileInputStream("manager.txt");
            DataInputStream in = new DataInputStream(fStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                managerCredentials.put(split[0], split[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initSubOrdinates() throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream(new File("sub_ordinate.txt"));
        if (fi.available() > 0) {
            ObjectInputStream oi = new ObjectInputStream(fi);
            userList = (List<User>) oi.readObject();
            usernameUserMap = userList.stream().collect(Collectors.toMap(User::getUsername, Function.identity()));
        } else {
            userList = new ArrayList<>();
            usernameUserMap = new HashMap<>();
        }
    }

    public static UserRepository getInstance() throws IOException, ClassNotFoundException {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    public User getUser(String username) {
        return usernameUserMap.get(username);
    }

    public User saveUser(User user) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("sub_ordinate.txt"));
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            userList.add(user);
            outputStream.writeObject(userList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userList;
    }

    //using map and checking for the values
    public UserRole getUserRoleByUsername(String username) {
        if (managerCredentials.containsKey(username)) {
            return UserRole.MANAGER;
        } else if (usernameUserMap.containsKey(username)) {
            usernameUserMap.get(username).getUserRole();
        }
        return null;
    }

    public boolean isManager(String username) {
        return managerCredentials.containsKey(username);
    }

    public boolean isValidManagerCredentials(String username, String password) {
        return managerCredentials.containsKey(username) && managerCredentials.get(username).equals(password);
    }
}
