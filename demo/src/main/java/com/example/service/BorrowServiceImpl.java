package com.example.service;

import com.example.domain.Book;
import com.example.domain.Borrow;
import com.example.domain.Member;
import com.example.repository.BookRepository;
import com.example.repository.BorrowRepository;
import com.example.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {

    private final BookRepository bookRepository;
    private final BorrowRepository borrowRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Borrow save(Long bookId, Long userId, Boolean isBorrowed) {
        Book book = bookRepository.findById(bookId).get();
        Member member = memberRepository.findById(4l).get();
        Borrow borrow = Borrow.builder()
                        .member(member)
                        .book(book)
                        .bookTitle(book.getTitle())
                        .bookWriter(book.getWriter())
                        .isBorrowed(isBorrowed)
                        .build();

        book.setBorrowed(isBorrowed);   // 책 대여상태 변경
        bookRepository.save(book);
        return borrowRepository.save(borrow);
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
