package com.remreren.justodoit.exception;

public class ErrorMessages {
    public static final ErrorMessage USER_NOT_FOUND = new ErrorMessage(404 , "USR_101", "User not found");
    public static final ErrorMessage USER_ALREADY_EXISTS = new ErrorMessage(400, "USR_102", "User already exists");

    public static final ErrorMessage BAD_CREDENTIALS = new ErrorMessage(400, "AUTH_101", "Invalid credentials");

    public static final ErrorMessage UNKNOWN_EXCEPTION = new ErrorMessage(500, "UNK_101", "Internal server error");
}
