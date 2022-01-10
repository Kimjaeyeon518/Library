package com.example.repository;

import com.example.domain.Book;
import com.example.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findByIdAndIsDisabled(Long id, Boolean isDisabled);

    int countByIsbn(String isbn);

    Page<Book> findAllByIsDisabled(Boolean isDisabled, Pageable pageable);

    Page<Book> findAllByIsBorrowedAndIsDisabled(Boolean isBorrowed, Boolean isDisabled, Pageable pageable);

    int countByIdAndIsDisabled(Long id, Boolean isDisabled);

    @Transactional
    @Modifying
    @Query("update Book b set b.isDisabled = true where b.id = :id")
    void updateById(@Param("id") Long id);
}