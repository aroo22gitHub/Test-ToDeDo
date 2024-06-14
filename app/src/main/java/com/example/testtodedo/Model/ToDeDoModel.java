package com.example.testtodedo.Model;

public class ToDeDoModel {
    private int id, status;
    private String task;
    private boolean isCompleted;

    public ToDeDoModel(int id, int status, String task) {
        this.id = id;
        this.status = status;
        this.task = task;
        this.isCompleted = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}



