package com.example.service;

import com.example.domain.Book;
import com.example.domain.Member;
import com.example.repository.BookRepository;
import com.example.exception.DataFormatException;
import com.example.exception.DeleteFailException;
import com.example.exception.DuplicatedException;
import com.example.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Book> findBook = bookRepository.findByIdAndIsDisabled(id, false);

        if(!findBook.isPresent())
            throw new NotFoundException("Cannot find book by ID : " + id);
        else
            return findBook.get();
    }

    @Override
    public Page<Book> findAll(String isBorrowed, Pageable pageable) {
        if(isBorrowed == null || "".equals(isBorrowed))
            return bookRepository.findAllByIsDisabled(false, pageable);
        else
            return bookRepository.findAllByIsBorrowedAndIsDisabled("1".equals(isBorrowed), false, pageable);

    }

    @Override
    public Book update(Long id, Book book) {
        Optional<Book> findBook = bookRepository.findByIdAndIsDisabled(book.getId(), false);

        if(!findBook.isPresent())
            throw new NotFoundException("Cannot find book by ID : " + book.getId());
        if(!id.equals(book.getId()))
            throw new DataFormatException("Book ID doesn't match ! -> first_ID : " + id + ", second_id :" + book.getId());
        if(bookRepository.countByIsbn(book.getIsbn()) > 0 && !id.equals(book.getId()))
            throw new DuplicatedException("Isbn is already exist : " + book.getIsbn());

        // 수정 시 isBorrowed 값이 날아가서 아래로 임시 조치
        book.setBorrowed(findBook.get().isBorrowed());

        return bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        if(!bookRepository.findByIdAndIsDisabled(id, false).isPresent())
            throw new NotFoundException("Cannot find book by ID : " + id);

        bookRepository.updateById(id);

        if(bookRepository.countByIdAndIsDisabled(id, true) == 0)
            throw new DeleteFailException("Failed to Delete with ID : " + id);
    }
}
