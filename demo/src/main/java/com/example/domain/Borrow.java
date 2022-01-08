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
@AllArgsConstructor
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
}
