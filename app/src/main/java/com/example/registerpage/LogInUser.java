package com.example.registerpage;

public class LogInUser
{
    String username;
    String password;

    public LogInUser() {
        //Empty Constructor For Firebase
    }


    public LogInUser(String username, String password)
    {
        this.username = username; //Parameterized for Program-Inhouse objects.
        this.password = password;
    }

    //Getters and Setters
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
}
