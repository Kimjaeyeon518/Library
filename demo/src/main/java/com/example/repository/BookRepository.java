package com.example.repository;

import com.example.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

    @Query("select b.id from Book b where b.isbn = :isbn")
    Long findIdByIsbn(@Param("isbn") String isbn);

    int countByIsbn(String isbn);
}