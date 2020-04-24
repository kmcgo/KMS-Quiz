package com.example.kmsquiz.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.kmsquiz.data.DB.Question;

public class Category implements Parcelable {
    public static final int PROGRAMMING = 1;
    public static final int GEOGRAPHY = 2;
    public static final int MATH = 3;
    public static final int DRUG = 4;
    public static final int SALES = 5;
    private static int incrementedID = 6;
    private int id;
    private String name;

    public Category() {
        incrementedID++;
        id = incrementedID;
    }

    public Category(String name) {
        this.name = name;
        incrementedID++;
        id = incrementedID;
    }
    protected Category(Parcel in) { // load question from parcel
        id = in.readInt();
        name = in.readString();
    }
    public int getIncrementedID() {
        return incrementedID;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString(){
        return getName();
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
