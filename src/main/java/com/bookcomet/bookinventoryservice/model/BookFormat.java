package com.bookcomet.bookinventoryservice.model;

public enum BookFormat {
    PDF("PDF"),
    EPUB("EPUB");

    public final String label;
    BookFormat(String label) {
        this.label = label;
    }
}
