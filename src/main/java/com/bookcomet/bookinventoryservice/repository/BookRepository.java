package com.bookcomet.bookinventoryservice.repository;

import com.bookcomet.bookinventoryservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByNameContainingAndAuthorsContaining(String name, String authors);
    List<Book> findBooksByNameContaining(String name);
    List<Book> findBooksByAuthorsContaining(String authors);
}