package com.example.kmsquiz;

public class Category {
    public static final int DRUG = 1;
    public static final int SALES = 2;
    public static final int DOCS = 3;
    private int id;
    private String name;



    public Category() {
    }

    public Category(String name){
        this.name = name;
    }

    // Returns the Category ID
    public int getId(){
        return id;
    }
    // Sets Category ID
    public void setId(int id){
        this.id = id;
    }
    // Returns Category Name
    public String getName(){
        return name;
    }
    // Sets Category Name
    public void setName(String name){
        this.name = name;
    }
    // returns name as a string
    @Override
    public String toString(){
        return getName();
    }

}
