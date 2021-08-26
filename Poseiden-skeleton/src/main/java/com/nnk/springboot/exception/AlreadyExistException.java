package com.nnk.springboot.exception;

public class AlreadyExistException extends RuntimeException {

    public AlreadyExistException(String s) {
        super(s);
    }
}
