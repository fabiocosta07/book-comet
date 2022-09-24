package com.bookcomet.bookinventoryservice.repository;

import com.bookcomet.bookinventoryservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}