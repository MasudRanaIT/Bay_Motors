package com.baymotors;

import java.util.ArrayList;
import java.util.List;

public class Mechanic extends User {
    private List<Task> tasks;

    public Mechanic(int id, String name, String email, String phoneNumber) {
        super(id, name, email, phoneNumber);
        this.tasks = new ArrayList<>();
    }
    
    public List<Task> getTasks() {
		return tasks;
	}

	public void addTask(Task task) {
        tasks.add(task);
    }

    public void viewTasks() {
        System.out.println("Tasks assigned to " + getName() + ":");
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Mechanic");
    }
}
