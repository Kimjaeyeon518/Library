//package com.example.service;
//
//import com.example.domain.User;
//import com.example.domain.UserRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class UserServiceTest {
//    @Autowired
//    private UserService userService;
//
//    @Test
//    void addUserSuccessfully() {
//        User user = User.builder().id(null).email("emailff").name("name").age(11).build();
//
//        User savedUser = userService.save(user);
//        Assertions.assertNotNull(savedUser);
//    }
//}