package com.example.admin.manager;


public class RegisteredEmployee {
    private String name, position, address, email, password;
    private String age;
    private String number;

    public RegisteredEmployee() {

    }

    public RegisteredEmployee(String name, String position, String address, String email, String password, String age, String number) {
        this.name = name;
        this.position = position;
        this.address = address;
        this.email = email;
        this.password = password;
        this.age = age;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAge() {
        return age;
    }

    public String getNumber() {
        return number;
    }
}