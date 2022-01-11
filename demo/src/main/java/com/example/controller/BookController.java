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
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public String findAll(Model model, Pageable pageable, @RequestParam(value = "isBorrowed", required = false) String isBorrowed){
        model.addAttribute("pages", bookService.findAll(isBorrowed, pageable));
        model.addAttribute("maxPage", 5);
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("isBorrowed", isBorrowed);
        return "book/bookList";
    }

    @GetMapping("/books/{id}")
    public String findOne(@PathVariable("id") Long id, Model model){
        model.addAttribute("book", bookService.findOne(id));

        return "book/bookDetail";
    }

    // @ModelAttribute Book book == @RequestParam string title ... , model.addAttribute("book", book)
    @PostMapping("/books")
    public String save(@ModelAttribute Book book){
        Book savedBook = bookService.save(book);
        return "redirect:/books/" + savedBook.getId();
    }

    @PostMapping("/books/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute Book book){
        bookService.update(id, book);
        return "redirect:/books/" + id;
    }

    @PutMapping("/books/{id}")
    public void delete(@PathVariable Long id){
        bookService.delete(id);
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

}
