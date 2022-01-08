package com.example.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "borrow")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Borrow extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(nullable = false)
    private String bookTitle;

    @Column(nullable = false)
    private String bookWriter;

    @Column(nullable = false)
    private boolean isBorrowed;

    @Builder
    public Borrow(Member member, Book book, String bookTitle, String bookWriter, boolean isBorrowed) {
        this.member = member;                   // 양방향 매핑
        this.member.getBorrowList().add(this);  // 양방향 매핑
        this.book = book;                       // 양방향 매핑
        this.book.getBorrowList().add(this);    // 양방향 매핑
        this.bookTitle = bookTitle;
        this.bookWriter = bookWriter;
        this.isBorrowed = isBorrowed;
    }
}
