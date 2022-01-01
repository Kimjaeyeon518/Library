package com.example.service;

import com.example.domain.Borrow;
import com.example.repository.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRepository borrowRepository;

    @Override
    public Borrow save(Borrow borrow) {
        return null;
    }

    @Override
    public Borrow findOne(Long id) {
        return null;
    }

    @Override
    public List<Borrow> findAll() {
        return null;
    }

    @Override
    public Borrow update(Borrow borrow) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
