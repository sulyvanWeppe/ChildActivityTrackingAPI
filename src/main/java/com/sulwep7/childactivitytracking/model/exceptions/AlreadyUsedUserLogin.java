package com.sulwep7.childactivitytracking.model.exceptions;

public class AlreadyUsedUserLogin extends Exception{
    public AlreadyUsedUserLogin() {
        super("Login is already used by another user");
    }
}
