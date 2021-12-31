package com.example.controller;

import com.example.domain.Book;
import com.example.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public String findAll(Model model){
        model.addAttribute("bookList", bookService.findAll());
        return "book/bookList";
    }

    @PostMapping("/books")
    public String save(Book book){
        bookService.save(book);
        return "contents/bookList";
    }

    @PutMapping("/books/{id}")
    public String update(Book book){
        bookService.update(book);
        return "contents/bookList";
    }

    @DeleteMapping("/books/{id}")
    public String delete(@RequestParam Long bookId){
        bookService.delete(bookId);
        return "contents/bookList";
    }
}
