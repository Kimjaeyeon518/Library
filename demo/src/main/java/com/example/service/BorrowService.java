package com.example.service;

import com.example.domain.Borrow;

import java.util.List;

public interface BorrowService {

    Borrow save(Borrow borrow);
    Borrow findOne(Long id);
    List<Borrow> findAll();
    Borrow update(Borrow borrow);
    boolean delete(Long id);
}
