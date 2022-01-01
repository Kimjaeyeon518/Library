package com.example.service;

import com.example.domain.Book;
import com.example.repository.BookRepository;
import com.example.exception.DataFormatException;
import com.example.exception.DeleteFailException;
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
    public Book update(Long id, Book book) {
        System.out.println("BookServiceImpl.update");
        if(!bookRepository.findById(book.getId()).isPresent())
            throw new NotFoundException("Cannot find book by ID : " + book.getId());
        if(id != book.getId())
            throw new DataFormatException("Book ID doesn't match ! -> first_ID : " + id + ", second_id :" + book.getId());
        if(bookRepository.countByIsbn(book.getIsbn()) > 0 && bookRepository.findIdByIsbn(book.getIsbn()) != book.getId())
            throw new DuplicatedException("Isbn is already exist : " + book.getIsbn());

        return bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        if(!bookRepository.findById(id).isPresent())
            throw new NotFoundException("Cannot find book by ID : " + id);

        bookRepository.deleteById(id);

        if(bookRepository.findById(id).isPresent())
            throw new DeleteFailException("Failed to Delete with ID : " + id);
    }
}
