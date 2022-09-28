package com.bookcomet.bookinventoryservice.repository;

import com.bookcomet.bookinventoryservice.model.Book;
import com.bookcomet.bookinventoryservice.model.BookInventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookInventoryRepositoryTest {
    @Autowired
    private BookInventoryRepository bookInventoryRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Book testBook;

    @BeforeEach
    void beforeEach() {
        testBook = entityManager.persist(new Book("book1","author1", "pub1",2000, "summary1"));
        entityManager.persist(new BookInventory(testBook, 10L));
    }

    @AfterEach
    void afterEach() {
        entityManager.clear();
    }

    @Test
    void findBookInventoriesByBook() throws Exception {
        BookInventory book = bookInventoryRepository.findBookInventoryByBook_Id(testBook.getId());
        assertThat(book).isNotNull();
        assertThat(book.getBook().getName()).isEqualTo("book1");
    }

    @Test
    void dontFindBookInventoriesByBook() throws Exception {
        BookInventory book = bookInventoryRepository.findBookInventoryByBook_Id(testBook.getId()+1);
        assertThat(book).isNull();
    }


}
