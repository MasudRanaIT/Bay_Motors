package com.baymotors;

import java.util.ArrayList;
import java.util.List;

public class Manager extends User {
    private List<String> notifications;

    public Manager(int id, String name, String email, String phoneNumber) {
        super(id, name, email, phoneNumber);
        this.notifications = new ArrayList<>();
    }

    public void allocateTask(Task task, Mechanic mechanic) {
        if (task != null && mechanic != null) {
            mechanic.addTask(task);
            task.setAssigned(true);
            System.out.println("Task allocated to " + mechanic.getName() + ": " + task.getDescription());
        } else {
            System.out.println("Task or Mechanic is invalid. Allocation failed.");
        }
    }

    public void postNotification(String message) {
        if (message != null && !message.trim().isEmpty()) {
            notifications.add(message);
            System.out.println("Notification posted: " + message);
        } else {
            System.out.println("Notification message cannot be empty.");
        }
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Manager");
    }

    public void notifyCustomers(List<Customer> customers, String message) {
        if (customers == null || customers.isEmpty()) {
            System.out.println("Customer list is empty. No notifications sent.");
            return;
        }

        System.out.println("Sending notifications to registered customers...");
        for (Customer customer : customers) {
            if (customer.isRegistered()) {
                System.out.println("Notifying " + customer.getName() + ": " + message);
            }
        }
    }

    public void notifyUnregisteredCustomers(List<Customer> customers, String message) {
        if (customers == null || customers.isEmpty()) {
            System.out.println("Customer list is empty. No notifications sent.");
            return;
        }

        System.out.println("Sending notifications to unregistered customers...");
        for (Customer customer : customers) {
            if (!customer.isRegistered()) {
                System.out.println("Notifying " + customer.getName() + ": " + message);
            }
        }
    }

    public List<String> getNotifications() {
        return notifications;
    }
}
