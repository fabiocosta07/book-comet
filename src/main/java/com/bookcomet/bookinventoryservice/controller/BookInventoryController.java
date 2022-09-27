package com.bookcomet.bookinventoryservice.controller;

import com.bookcomet.bookinventoryservice.dto.BookInventoryDTO;
import com.bookcomet.bookinventoryservice.exception.BusinessValidationException;
import com.bookcomet.bookinventoryservice.model.BookInventory;
import com.bookcomet.bookinventoryservice.repository.BookInventoryRepository;
import com.bookcomet.bookinventoryservice.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class BookInventoryController {

    private final BookInventoryRepository repository;

    private final BookRepository bookRepository;

    BookInventoryController(BookInventoryRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/book-inventories")
    List<BookInventory> all() {
        return repository.findAll();
    }

    @PostMapping("/book-inventories")
    BookInventory newBookInventory(@RequestBody BookInventoryDTO newBook) {
        if (newBook.getQuantity() < 0) {
            throw new BusinessValidationException("Quantity cannot be negative");
        }
        return repository.save(convertDTO(newBook));
    }

    @DeleteMapping("/book-inventories/{id}")
    void deleteBookInventory(@PathVariable Long id) {
        repository.deleteById(id);
    }

    private BookInventory convertDTO(BookInventoryDTO dto) {
        final BookInventory bi = new BookInventory();
        BeanUtils.copyProperties(dto, bi);
        bi.setBook(bookRepository.findById(dto.getBookId()).get());
        return bi;
    }
}