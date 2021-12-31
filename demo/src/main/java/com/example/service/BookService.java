package com.example.service;

import com.example.domain.Book;
import com.example.domain.User;

import java.util.List;

public interface BookService {

    Book save(Book book);
//    Book findOne(Long id);
    List<Book> findAll();
    Book update(Book book);
    boolean delete(Long id);
}
