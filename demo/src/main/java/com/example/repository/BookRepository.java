package com.example.repository;

import com.example.domain.Book;
import com.example.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

    int countByIsbn(String isbn);

    @Query("select b from Book b where b.isDisabled = false")
    Page<Book> findAll(Pageable pageable);

    @Query("select b from Book b where b.isBorrowed = :isBorrowed and b.isDisabled = false")
    Page<Book> findAll(@Param("isBorrowed") Boolean isBorrowed, Pageable pageable);

    @Query("update Book b set b.isDisabled = true where b.id = :id")
    void delete(@Param("id") Long id);

    @Query("select count(b) from Book b where b.isDisabled = true and b.id = :id")
    int checkDisabled(@Param("id") Long id);
}