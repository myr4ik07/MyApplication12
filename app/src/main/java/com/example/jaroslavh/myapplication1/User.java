package com.example.jaroslavh.myapplication1;

/**
 * Created by jaroslav.h on 30.05.2017.
 */

public class User {

    int id;
    String name;
    int pass;

    User(int id, String name, int pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    User(){

    }

    @Override
    public String toString() {
        return name;
    }

}
