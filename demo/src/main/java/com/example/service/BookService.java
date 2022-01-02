package com.example.service;

import com.example.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    Book save(Book book);
    Book findOne(Long id);
    Page<Book> findAll(Pageable pageable);
    Book update(Long id, Book book);
    void delete(Long id);
}
