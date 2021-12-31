package com.example.dto;

import com.example.domain.User;
import lombok.Getter;

@Getter
public class ResponseUserDto {
    private final Long id;
    private final String email;
    private final String name;
    private final int age;

    public ResponseUserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.age = user.getAge();
    }
}
