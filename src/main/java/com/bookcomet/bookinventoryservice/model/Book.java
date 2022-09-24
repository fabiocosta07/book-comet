package com.bookcomet.bookinventoryservice.model;

import javax.persistence.*;

@Entity
@Table(name = "book", uniqueConstraints = { @UniqueConstraint(name = "UniqueBooks", columnNames = { "name", "authors"}) })
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="authors")
    private String authors;

    @Column(name="publisher")
    private String publisher;

    @Column(name="yearOfPublication")
    private Integer yearOfPublication;

    @Column(name="summary")
    private String summary;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Integer yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
