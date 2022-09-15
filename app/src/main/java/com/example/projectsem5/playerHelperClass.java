package com.example.projectsem5;

public class playerHelperClass {

    String Email,Firstname,Lastname,Username,Password;

    public playerHelperClass() {

    }

    public playerHelperClass(String email, String firstName, String lastName, String username, String password) {
        //Uid=uid;
        Email = email;
        Firstname = firstName;
        Lastname = lastName;
        Username = username;
        Password = password;
    }


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstName) {
        Firstname = firstName;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastName) {
        Lastname = lastName;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
