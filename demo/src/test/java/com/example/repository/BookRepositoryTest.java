package com.example.repository;

import com.example.domain.Book;
import com.example.exception.DuplicatedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void save() {
        for(int i=51;i<300;i++) {
            Book book = new Book();
            book.setTitle("title " + i);
            book.setWriter("writer " + i);
            book.setIsbn("ISBN " + i);
            book.setContent("content " + i);
            bookRepository.save(book);
        }
    }
}