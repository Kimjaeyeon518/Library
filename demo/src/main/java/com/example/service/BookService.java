package com.example.service;

import com.example.domain.Book;
import com.example.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    Book save(Book book);
    Book findOne(Long id);
    Page<Book> findAll(String isBorrowed, Pageable pageable);
    Book update(Long id, Book book);
    void delete(Long id);
}
