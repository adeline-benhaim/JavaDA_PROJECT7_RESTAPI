package com.nnk.springboot.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String s) {
        super(s);
    }
}
