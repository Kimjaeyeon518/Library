package com.example.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "borrow")
@Getter
@NoArgsConstructor
@ToString
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


}
