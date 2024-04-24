package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Book;
import com.example.demo.model.User;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class BookController {
    @Autowired
    public BookService bookService;
    @Autowired
    public UserService uService;
    @PostMapping("/postt")
    @ResponseStatus
    public ResponseEntity<String> postMethodName(@RequestBody List<Book> book) {
        for(Book b:book){
        if(getById(b.getBookId())==null){    
        bookService.postBook(b);
        }
        else
          {
            return new ResponseEntity<>("Id already existed",HttpStatus.NOT_ACCEPTABLE);
          }
    }
           return new ResponseEntity<>("Saved",HttpStatus.CREATED);
        
    }
    
    @GetMapping("/id/{value}")
    public Book getById(@PathVariable("value") int value) {
        return bookService.getBookById(value);
    }

    @GetMapping("/name/{value}")
    public List<Book> getByName(@PathVariable("value") String value) {
        return bookService.getBookByTitle(value);
    }

    @GetMapping("/author/{value}")
    public List<Book> getByCategory(@PathVariable("value") String value) {
        return bookService.getBookByAuthor(value);
    }
    
    @GetMapping("/price/{value}")
    public List<Book> getMethodName(@PathVariable("value") double price) {
        return bookService.getBookByPrice(price);
    }
    
    @GetMapping("/category/{value}")
    public List<Book> getMethodName(@PathVariable("value") String category) {
        return bookService.getBookByCategory(category);
    }
    
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int  id){
        if(getById(id)!=null){
            bookService.deleteBookById(id);
            return "deleted";
        }
        return "Book not exists";
    }

    @PutMapping("/update")
    public String putMethodName(@RequestParam int id,@RequestParam String price) {
        Book book=bookService.getBookById(id);
        if(book!=null){
             book.setBookTitle(price);
             bookService.postBook(book);
             return "updated";
        }
        return "Book not exists";
    }


//PAGINATION AND SORTING


    @GetMapping("/page/{pn}/{ps}")
    public Page<Book> getMethod(@PathVariable("pn") int pageNo,@PathVariable("ps") int pageSize) {
        return bookService.pagination(pageNo, pageSize);
    }
    @GetMapping("/pagelist/{pn}/{ps}")
    public List<Book> getMethodName(@PathVariable("pn") int pageNo,@PathVariable("ps") int pageSize) {
        return bookService.paginations(pageNo, pageSize);
    }

    @GetMapping("/pageSort")
    public Page<Book> getMethodSort(@RequestParam int pageNo,@RequestParam int pageSize,@RequestParam String name) {
        return bookService.paginationSort(pageNo, pageSize,name);
    }


//ONE TO ONE AND ONE TO MANY RELATIONSHIPS OF TABLE


@PostMapping("/book/author/{authorId}")
public ResponseEntity<Book> postMethodName(@RequestBody Book b,@PathVariable int authorId) {
   try{
      User a=uService.getUser(authorId);
      b.setUser(a);
      return new ResponseEntity<>( bookService.postBook(b),HttpStatus.CREATED);
   }
   catch(Exception e){
    return new ResponseEntity<>( null,HttpStatus.INTERNAL_SERVER_ERROR);
   }
}
}
