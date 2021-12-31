package com.example.service;

import com.example.domain.Book;
import com.example.domain.BookRepository;
import com.example.exception.DuplicatedException;
import com.example.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final boolean DELETE_FAIL = false;
    private final boolean DELETE_SUCCESS = true;

    @Override
    public Book save(Book book) {
        if(bookRepository.findByIsbn(book.getIsbn()).isPresent())
            throw new DuplicatedException("Isbn is already exist : " + book.getIsbn());
        else
            return bookRepository.save(book);
    }

    @Override
    public Book findOne(Long id) {
        if(!bookRepository.findById(id).isPresent())
            throw new NotFoundException("Cannot find book by ID : " + id);
        else
            return bookRepository.findById(id).get();
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book update(Book book) {
        if(!bookRepository.findById(book.getId()).isPresent())
            throw new NotFoundException("Cannot find book by ID : " + book.getId());
        if(bookRepository.findByIsbn(book.getIsbn()).isPresent())
            throw new DuplicatedException("Isbn is already exist : " + book.getIsbn());

        return bookRepository.save(book);
    }

    @Override
    public boolean delete(Long id) {
        if(!bookRepository.findById(id).isPresent())
            throw new NotFoundException("Cannot find book by ID : " + id);

        bookRepository.deleteById(id);

        if(bookRepository.findById(id).isPresent())
            return DELETE_FAIL;
        else
            return DELETE_SUCCESS;
    }
}
