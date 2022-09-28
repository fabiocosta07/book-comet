package com.bookcomet.bookinventoryservice.repository;


import com.bookcomet.bookinventoryservice.model.BookInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookInventoryRepository extends JpaRepository<BookInventory, Long> {
    BookInventory findBookInventoryByBook_Id(Long bookId);
}