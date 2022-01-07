package com.example.controller;

import com.example.domain.Book;
import com.example.jwt.JwtTokenProvider;
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
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public String findAll(Model model, Pageable pageable, @RequestParam(value = "isBorrowed", required = false) String isBorrowed){
        model.addAttribute("pages", bookService.findAll(isBorrowed, pageable));
        model.addAttribute("maxPage", 5);
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("isBorrowed", isBorrowed);
        return "book/bookList";
    }

    @GetMapping("/{id}")
    public String findOne(@PathVariable("id") Long id, Model model){
        model.addAttribute("book", bookService.findOne(id));
        return "book/bookDetail";
    }

    // @ModelAttribute Book book == @RequestParam string title ... , model.addAttribute("book", book)
    @PostMapping
    public String save(@ModelAttribute Book book){
        bookService.save(book);
        return "book/bookDetail";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute Book book){
        bookService.update(id, book);
        return "book/bookDetail";
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        bookService.delete(id);
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("book", bookService.findOne(id));
        return "book/bookUpdate";
    }

    @GetMapping("/add")
    public String add(){
        return "book/bookSave";
    }
}
