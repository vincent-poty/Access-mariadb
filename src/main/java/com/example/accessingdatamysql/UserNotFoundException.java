package com.example.accessingdatamysql;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(Integer id) {
        super("Not found User" );
    }
}
