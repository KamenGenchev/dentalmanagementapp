package com.dentalmanagementapp.exception.custom;

public class NoAuthorizedUserException extends RuntimeException {
    public NoAuthorizedUserException(String message) {
        super(message);
    }

    public NoAuthorizedUserException() {
        super("Unable to fetch current user.");
    }
}
