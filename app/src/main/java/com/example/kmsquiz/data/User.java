package com.example.kmsquiz.data;

public class User {
    private String Pass;
    private String name;
    private int UserId;

    public User()
    {

    }

    public int getId(){
        return UserId;
    }

    public void setId(int id){
        UserId = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPass(){
        return Pass;
    }

    public void setPass(String pass){
        Pass = pass;
    }
}
