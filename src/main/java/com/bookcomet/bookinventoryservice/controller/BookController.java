package com.bookcomet.bookinventoryservice.controller;

import java.util.List;

import com.bookcomet.bookinventoryservice.dto.BookDTO;
import com.bookcomet.bookinventoryservice.exception.BookNotFoundException;
import com.bookcomet.bookinventoryservice.model.*;
import com.bookcomet.bookinventoryservice.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BookController {

    private final BookRepository repository;

    BookController(BookRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/books")
    List<Book> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/books")
    ResponseEntity<Book> newBook(@RequestBody BookDTO newBook) {
        return new ResponseEntity<>(repository.save(convertDTO(newBook)), HttpStatus.CREATED);
    }

    // Single item

    @GetMapping("/books/{id}")
    Book one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @PutMapping("/books/{id}")
    Book replaceBook(@RequestBody Book newBook, @PathVariable Long id) {

        return repository.findById(id)
                .map(book -> {
                    book.setName(newBook.getName());
                    book.setAuthors(newBook.getAuthors());
                    book.setPublisher(newBook.getPublisher());
                    book.setSummary(newBook.getSummary());
                    book.setYearOfPublication(newBook.getYearOfPublication());
                    return repository.save(book);
                })
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
    }
    private Book convertDTO(BookDTO dto) {
        Book book = new Book();
        if (dto.getFormat() != null) {
            switch (dto.getFormat()) {
                case PDF:
                    book = new PDFBook();
                    break;
                case EPUB:
                    book = new EPUBBook();
                    break;
            }
        }
        BeanUtils.copyProperties(dto, book);
        return book;
    }
}
