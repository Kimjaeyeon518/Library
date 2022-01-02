package com.example.controller;

import com.example.domain.Book;
import com.example.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public String findAll(Model model, Pageable pageable){
        System.out.println(pageable.getPageNumber());
        model.addAttribute("pages", bookService.findAll(pageable));
        model.addAttribute("maxPage", 5);
        model.addAttribute("currentPage", pageable.getPageNumber());
        return "book/bookList";
    }

    @GetMapping("/books/{id}")
    public String findOne(@PathVariable("id") Long id, Model model){
        model.addAttribute("book", bookService.findOne(id));
        return "book/bookDetail";
    }

    @GetMapping("/books/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("book", bookService.findOne(id));
        return "book/bookUpdate";
    }

    @GetMapping("/books/add")
    public String add(){
        return "book/bookSave";
    }

    // @ModelAttribute("item") Book book
    // ==> @RequestParam string title ... , model.addAttribute("book", book)
    @PostMapping(value = "/books")
    public String save(@ModelAttribute Book book){
        bookService.save(book);
        return "book/bookDetail";
    }

    @PostMapping("/books/{id}")
    public String update(@PathVariable("id") Long id, Book book){
        bookService.update(id, book);
        return "book/bookDetail";
    }

    @DeleteMapping("/books/{id}")
    public void delete(@PathVariable Long id){
        bookService.delete(id);
    }
}
