package com.stanleyyeung.crudrestapi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.stanleyyeung.crudrestapi.exception.NotFoundException;
import com.stanleyyeung.crudrestapi.model.Book;

@Service
public class BooksService {

    private List<Book> bookList = new ArrayList<>(Arrays.asList(
            new Book(1, "Spring Boot", "stanley", true),
            new Book(2, "React FrameWork", "stanley", false),
            new Book(3, "Java FrameWork", "ben", true)
    ));
    
    public List<Book> getAllBooksList() {
    	return bookList;
    }
    
    public void addBook(Book book) {
    	bookList.add(book);
    }
    
    public List<Book> findBooksByAuthor(String author) {
    	
    	List<Book> filteredBooks = bookList.stream()
    				.filter(book -> book.getAuthor().equalsIgnoreCase(author)).toList();
    	
		return filteredBooks;
    }

    public List<Book> findBooksByStatus(Boolean status) {
    	
    	List<Book> filteredBooks = bookList.stream()
    				.filter(book -> book.getPublished().equals(status))
    				.toList();
    	
		return filteredBooks;
    }
    
    public List<Book> findBooksByAuthorAndStatus(String author, Boolean status) {
    	
    	List<Book> filteredBooks = bookList.stream()
    				.filter(book -> book.getAuthor().equalsIgnoreCase(author) && book.getPublished().equals(status))
    				.toList();
    	
		return filteredBooks;
    }
    
    public Book findBookById(long id) {
    	
        Book book =	bookList.stream()
        		.filter(item -> item.getId() == id)
        		.findFirst()
        		.orElseThrow(() -> new NotFoundException("Book not found - " + id));
        
        return book;
    	
    } 
    
    public void deleteBook(long id) {
    	bookList.removeIf(book -> book.getId() == id);
    } 
    
}
