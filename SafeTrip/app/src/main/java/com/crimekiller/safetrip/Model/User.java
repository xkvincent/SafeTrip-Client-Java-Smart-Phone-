package com.crimekiller.safetrip.Model;

import java.util.ArrayList;

/**
 * Created by Wenlu on 4/2/16.
 */
public class User {
    private String name;
    private static ArrayList<User> users;
    public User(String name){
        this.name = name;
    }
    private String ID;

    public static ArrayList<User> getUser(){
        users = new ArrayList<User>();
        User a = new User("UserA");
        User b = new User("UserB");

        users.add(a);
        users.add(b);

        return users;
    }

    public String getName() {
        return name;
    }

    public static User getUserByName(String name){
        for( User user: users ){
            if( user.getName().equals(name))
                return user;
        }
        return null;
    }
}
