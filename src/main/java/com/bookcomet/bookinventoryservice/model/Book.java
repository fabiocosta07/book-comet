package com.bookcomet.bookinventoryservice.model;

import javax.persistence.*;

@Entity
@Table(name = "book", uniqueConstraints = { @UniqueConstraint(name = "UniqueBooks", columnNames = { "name", "authors"}) })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="format",
        discriminatorType = DiscriminatorType.STRING)
public class Book {

    public Book() {
    }
    public Book(String name, String authors, String publisher, Integer yearOfPublication, String summary) {
        this.name = name;
        this.authors = authors;
        this.publisher = publisher;
        this.yearOfPublication = yearOfPublication;
        this.summary = summary;
    }

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

    @Column(name="format", updatable=false, insertable=false)
    private String format;


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
    public String getFormat() {
        return format;
    }
}
