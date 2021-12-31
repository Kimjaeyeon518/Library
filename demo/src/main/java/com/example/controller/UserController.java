package com.example.controller;

import com.example.domain.User;
import com.example.service.UserService;
import com.example.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/save")
    public void save() {
        User user = User.builder().id(null).email("sssf").name("name").age(11).build();
        System.out.println("UserController.save");
        userService.save(user);
    }

    @GetMapping(value = "/u")
    public void update() {
        User user = User.builder().id(1l).email("ssssssssf").name("nsssssame").age(111).build();
        System.out.println("UserController.update");
        userService.update(user);
    }

    @GetMapping(value = "/r")
    public void read() {
        System.out.println("UserController.read");
        System.out.println(userService.findOne(4l));
        System.out.println(userService.findAll());
    }

    @GetMapping(value = "/d")
    public void delete() {
        System.out.println("UserController.delete");
        userService.delete(1l);
    }
}
