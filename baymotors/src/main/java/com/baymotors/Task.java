package com.baymotors;

public class Task {
    private int taskId;
    private String description;
    private int priority;
    private boolean isAssigned;
    private boolean isComplete;

    public Task(int taskId, String description, int priority) {
        this.taskId = taskId;
        this.description = description;
        this.priority = priority;
        this.isAssigned = false;
        this.isComplete = false;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority; // Getter for priority
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }

    public void markComplete() {
        isComplete = true;
        System.out.println("Task " + taskId + " marked as complete.");
    }

    @Override
    public String toString() {
        return "Task ID: " + taskId + ", Description: " + description + ", Priority: " + priority +
               ", Assigned: " + isAssigned + ", Complete: " + isComplete;
    }
}
