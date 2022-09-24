package com.bookcomet.bookinventoryservice.repository;


import com.bookcomet.bookinventoryservice.model.BookInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookInventoryRepositoy extends JpaRepository<BookInventory, Long> {
}