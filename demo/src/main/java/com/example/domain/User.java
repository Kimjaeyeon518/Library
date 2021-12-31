package com.example.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Builder
    public User(Long id, String email, String name, int age) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.age = age;
    }
}
