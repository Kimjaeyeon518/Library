package com.example.repository;

import com.example.domain.Book;
import com.example.exception.DuplicatedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void save() {
        for(int i=0;i<30;i++) {
            Book book = new Book();
            book.setTitle("title " + i);
            book.setWriter("writer " + i);
            book.setIsbn("ISBN " + i);
            book.setContent("content " + i);
            book.setCreatedDate(LocalDateTime.now());
            book.setUpdatedDate(LocalDateTime.now());
            bookRepository.save(book);
        }
    }
}