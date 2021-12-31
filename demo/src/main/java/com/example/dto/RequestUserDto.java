package com.example.dto;

import com.example.domain.User;
import lombok.*;

@Getter
@NoArgsConstructor
public class RequestUserDto {

    private Long id;
    private String email;
    private String name;
    private int age;

    @Builder
    public RequestUserDto(Long id, String email, String name, int age) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.age = age;
    }

    public User toEntity() {
        return User.builder()
                .id(id)
                .email(email)
                .name(name)
                .age(age)
                .build();
    }
}
