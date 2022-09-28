package com.bookcomet.bookinventoryservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import com.bookcomet.bookinventoryservice.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void beforeEach() {
        entityManager.persist(new Book("book1","author1", "pub1",2000, "summary1"));
        entityManager.persist(new Book("book2","author2", "pub2",2000, "summary2"));
        entityManager.persist(new Book("book3","author3", "pub3",2000, "summary3"));
    }

    @AfterEach
    void afterEach() {
        entityManager.clear();
    }

    @Test
    void findAllBooks() throws Exception {
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(3);
    }

    @Test
    void findBooksByName() throws Exception {
        List<Book> books = bookRepository.findBooksByNameContaining("book1");
        assertThat(books).hasSize(1);
    }

    @Test
    void findBooksByAuthors() throws Exception {
        List<Book> books = bookRepository.findBooksByAuthorsContaining("author1");
        assertThat(books).hasSize(1);
    }

    @Test
    void findBooksByNameAndAuthors() throws Exception {
        entityManager.clear();
        entityManager.persist(new Book("book1","author1", "pub1",2000, "summary1"));
        entityManager.persist(new Book("book1","other author", "pub1",2000, "summary1"));

        List<Book> books = bookRepository.findBooksByNameContainingAndAuthorsContaining("book1","author1");
        assertThat(books).hasSize(1);
    }

}
