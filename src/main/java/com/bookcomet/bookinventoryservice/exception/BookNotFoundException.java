package com.bookcomet.bookinventoryservice.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("Could Not find Book="+id);
    }
}