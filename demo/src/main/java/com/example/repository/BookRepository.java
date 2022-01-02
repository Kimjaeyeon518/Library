package com.example.repository;

import com.example.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

    int countByIsbn(String isbn);

    Page<Book> findAll(Pageable pageable);

    @Query(value = "select b from Book b where b.isBorrowed = :isBorrowed",
                    countQuery = "select count(b) from Book b")
    Page<Book> findAllByIsBorrowed(@Param("isBorrowed") boolean isBorrowed, Pageable pageable);

}