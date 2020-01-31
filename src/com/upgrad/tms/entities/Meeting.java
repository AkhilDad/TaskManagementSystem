package com.upgrad.tms.entities;

/**
 * This class represents meeting type Tasks
 */
//No need for serialization as superclass is serialized
public class Meeting extends Task {
    private String location;
    private String url;
    private String agenda;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    @Override
    public void printSubTaskProperties() {
        System.out.println("Location: " + getLocation());
        System.out.println("Url: " + getUrl());
        System.out.println("Agenda: " + getAgenda());
    }
}
