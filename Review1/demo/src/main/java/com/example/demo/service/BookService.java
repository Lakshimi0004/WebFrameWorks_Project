package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

@Service
public class BookService {
    @Autowired
    public BookRepository bookRepository;

    public Book getBookById(int id){
         return bookRepository.findById(id).orElse(null);
    }

    public List<Book> getBookByAuthor(String authorName){
        return bookRepository.findByAuthorName(authorName);
    }

    public List<Book> getBookByCategory(String categoryName){
        return bookRepository.findByCategory(categoryName);
    }


    public List<Book> getBookByTitle(String title){
        return bookRepository.findByBookTitleIgnoreCaseContaining(title);
    }

    public List<Book> getBookByPrice(double price){
        return bookRepository.findByPriceLessThan(price);
    }

    public Book postBook(Book b){
        return bookRepository.save(b);
    }

    public void deleteBookById(int id){
          bookRepository.deleteById(id);
    }

    public Page<Book> pagination(int pageNo,int pageSize){
          return bookRepository.findAll(PageRequest.of(pageNo,pageSize));
    }
    
    public List<Book> paginations(int pageNo,int pageSize){
        return bookRepository.findAll(PageRequest.of(pageNo,pageSize)).getContent();
    }

    public Page<Book> paginationSort(int pageNo,int pageSize,String name){
          return bookRepository.findAll(PageRequest.of(pageNo,pageSize,Sort.by(Direction.DESC,name)));
    }
}
