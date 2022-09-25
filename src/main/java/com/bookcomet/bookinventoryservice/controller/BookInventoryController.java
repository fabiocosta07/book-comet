package com.bookcomet.bookinventoryservice.controller;

import com.bookcomet.bookinventoryservice.model.BookInventory;
import com.bookcomet.bookinventoryservice.repository.BookInventoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class BookInventoryController {

    private final BookInventoryRepository repository;

    BookInventoryController(BookInventoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/book-inventories")
    List<BookInventory> all() {
        return repository.findAll();
    }

    @PostMapping("/book-inventories")
    BookInventory newBookInventory(@RequestBody BookInventory newBook) {
        return repository.save(newBook);
    }

    @DeleteMapping("/book-inventories/{id}")
    void deleteBookInventory(@PathVariable Long id) {
        repository.deleteById(id);
    }
}