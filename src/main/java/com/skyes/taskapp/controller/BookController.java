package com.skyes.taskapp.controller;

import com.skyes.taskapp.entity.Book;
import com.skyes.taskapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{book_id}")
    public ResponseEntity<Book> getBook(@PathVariable String book_id) {
        Optional<Book> book = bookService.getBookById(Integer.parseInt(book_id));
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Book addBook(@RequestBody Map<String, String> body) {
        Book book = new Book();
        book.setTitle(body.get("title"));
        book.setAuthor(body.get("author"));
        return bookService.saveBook(book);
    }


    @PutMapping("/{book_id}")
    public ResponseEntity<String> updateBook(@PathVariable String book_id, @RequestBody Map<String, String> body) {
        Optional<Book> current =  bookService.getBookById(Integer.parseInt(book_id));
        if(current.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Book book = current.get();
        if(body.containsKey("title")) {
            book.setTitle(body.get("title"));
        }
        if(body.containsKey("author")) {
            book.setAuthor(body.get("author"));
        }
        if(body.containsKey("is_available")) {
            book.setIs_available(Boolean.parseBoolean(body.get("is_available")));
        }
        bookService.saveBook(book);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{book_id}")
    public ResponseEntity<String> deleteBook(@PathVariable String book_id) {
        Optional<Book> current =  bookService.getBookById(Integer.parseInt(book_id));
        if(current.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.deleteBook(Integer.parseInt(book_id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
