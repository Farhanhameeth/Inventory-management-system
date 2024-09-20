package com.dev.pos.dto.tm;

import javafx.scene.control.Button;

public class CustomerTm {
    int id;
    String email;
    String name;
    String contact;
    double salary;
    Button button;

    public CustomerTm() {
    }

    public CustomerTm(int id, String email, String name, String contact, double salary, Button button) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.contact = contact;
        this.salary = salary;
        this.button = button;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
