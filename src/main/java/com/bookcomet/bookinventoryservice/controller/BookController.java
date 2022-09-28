package com.bookcomet.bookinventoryservice.controller;

import java.util.List;

import com.bookcomet.bookinventoryservice.dto.BookDTO;
import com.bookcomet.bookinventoryservice.exception.BookNotFoundException;
import com.bookcomet.bookinventoryservice.exception.BusinessValidationException;
import com.bookcomet.bookinventoryservice.model.*;
import com.bookcomet.bookinventoryservice.repository.BookInventoryRepository;
import com.bookcomet.bookinventoryservice.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
class BookController {

    private final BookRepository repository;
    private final BookInventoryRepository bookInventoryRepository;

    BookController(BookRepository repository, BookInventoryRepository bookInventoryRepository) {
        this.repository = repository;
        this.bookInventoryRepository = bookInventoryRepository;
    }

    @GetMapping("/books")
    List<Book> search(@RequestParam(required = false) String name, @RequestParam(required = false) String authors) {
        if (StringUtils.hasLength(name) && StringUtils.hasLength(authors)) {
            return repository.findBooksByNameContainingAndAuthorsContaining(name, authors);
        } else if (StringUtils.hasLength(name)) {
            return repository.findBooksByNameContaining(name);
        } else if (StringUtils.hasLength(authors)) {
            return repository.findBooksByAuthorsContaining(authors);
        } else {
            return repository.findAll();
        }
    }

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    Book newBook(@RequestBody BookDTO newBook) {
        if (repository.findBooksByNameAndAuthors(newBook.getName(), newBook.getAuthors()) == null) {
            return repository.save(convertDTO(newBook));
        }
        throw new BusinessValidationException("Book already exists");
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
        BookInventory bookInventory = bookInventoryRepository.findBookInventoryByBook_Id(id);
        if (bookInventory == null || bookInventory.getQuantity() == 0  ) {
            repository.deleteById(id);
            return;
        }
        throw new BusinessValidationException("Cannot delete book with positive inventory quantity");
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
