package com.wagonfly.todolist;

/**
 * Created by Kapil Katiyar on 6/6/2018.
 */

public class TodoListPojo
{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String task;
    private int id;
    private String status;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
