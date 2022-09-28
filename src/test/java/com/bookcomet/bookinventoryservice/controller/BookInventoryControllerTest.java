package com.bookcomet.bookinventoryservice.controller;

import com.bookcomet.bookinventoryservice.model.Book;
import com.bookcomet.bookinventoryservice.model.BookInventory;
import com.bookcomet.bookinventoryservice.repository.BookInventoryRepository;
import com.bookcomet.bookinventoryservice.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookInventoryController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class BookInventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BookInventoryRepository bookInventoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldSaveBooKInventory() throws Exception {
        BookInventory bookInventory = new BookInventory(new Book(), 10L);
        final String expectedContent = objectMapper.writeValueAsString(bookInventory);
        when(bookRepository.findById(any())).thenReturn(Optional.of(new Book()));
        when(bookInventoryRepository.save(any())).thenReturn(bookInventory);
        this.mockMvc.perform(post("/book-inventories")
                .content(expectedContent)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedContent));
        verify(bookInventoryRepository).save(any());
    }

    @Test
    public void shouldNotSaveBooKInventory() throws Exception {
        BookInventory bookInventory = new BookInventory(new Book(), -10L);
        final String expectedContent = objectMapper.writeValueAsString(bookInventory);
        when(bookRepository.findById(any())).thenReturn(Optional.of(new Book()));
        when(bookInventoryRepository.save(any())).thenReturn(bookInventory);
        this.mockMvc.perform(post("/book-inventories")
                        .content(expectedContent)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Quantity cannot be negative"));
        verify(bookInventoryRepository,never()).save(any());
    }


}