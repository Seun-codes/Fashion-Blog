package com.elizabeth.restblogweek9.Exceptions;

public class PostNotFoundException  extends RuntimeException{
    public PostNotFoundException(String message) {
        super(message);
    }
}
