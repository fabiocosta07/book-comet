package com.bookcomet.bookinventoryservice.controller;

import com.bookcomet.bookinventoryservice.model.Book;
import com.bookcomet.bookinventoryservice.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getBooksShouldReturnBooks() throws Exception {
        List testList = Arrays.asList(new Book());
        final String expectedContent = objectMapper.writeValueAsString(testList);
        when(bookRepository.findAll()).thenReturn(testList);
        this.mockMvc.perform(get("/books")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedContent));
        verify(bookRepository).findAll();
    }
    @Test
    public void postBooksShouldCreateNewBook() throws Exception {
        Book testBook = new Book();
        String expectedContent = objectMapper.writeValueAsString(testBook);
        when(bookRepository.save(any())).thenReturn(testBook);
        this.mockMvc.perform(post("/books")
                        .content(expectedContent)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedContent));
        verify(bookRepository).save(any());
    }
    @Test
    public void putNotesShouldUpdateNote() throws Exception {
        Book testBook = new Book();
        String expectedContent = objectMapper.writeValueAsString(testBook);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any())).thenReturn(testBook);
        this.mockMvc.perform(put("/books/1")
                        .content(expectedContent)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedContent));
        verify(bookRepository).save(any());
    }
    @Test
    public void deleteNotesShouldDeleteNote() throws Exception {
        this.mockMvc.perform(delete("/books/1"));
        verify(bookRepository).deleteById(1L);
    }
}