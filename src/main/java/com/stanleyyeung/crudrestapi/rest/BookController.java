package com.stanleyyeung.crudrestapi.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stanleyyeung.crudrestapi.exception.NotFoundException;
import com.stanleyyeung.crudrestapi.model.Book;
import com.stanleyyeung.crudrestapi.request.BookRequest;
import com.stanleyyeung.crudrestapi.service.BooksService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
public class BookController {
	
	@Autowired
	private BooksService booksService;
	
	@ResponseStatus(HttpStatus.OK)
    @GetMapping("/books")
    public List<Book> getBooksByAuthorAndOrStatus(@RequestParam(required = false) String author, 
    		@RequestParam(required = false) Boolean status) {
		
    	if (author == null) {
    		
    		List<Book> bookList = booksService.getAllBooksList();
    		
    		if(status != null) {
    			List<Book> filteredBooksByStatus = booksService.findBooksByStatus(status);
    			return filteredBooksByStatus;
    		}
    		
    		return bookList;
    	}
    	
    	List<Book> filteredBooksByAuthor = booksService.findBooksByAuthor(author);

        if (filteredBooksByAuthor.isEmpty()) {
            throw new NotFoundException("Author not found - " + author);
        }
    	
    	if(status != null) {
    		List<Book> filteredBooksByStatusAndAuthor = booksService.findBooksByAuthorAndStatus(author, status);
    		return filteredBooksByStatusAndAuthor;
    		
    	}
    	
        return filteredBooksByAuthor;
    }
	
    @ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/books")
	public void addBook(@Valid @RequestBody BookRequest bookRequest) {
    	
    	if (!isAlphaNumeric(bookRequest.getAuthor())) {
    		throw new RuntimeException("Author is not alphanumeric.");
    	}
    	
    	if (!isAlphaNumeric(bookRequest.getTitle())) {
    		throw new RuntimeException("Title is not alphanumeric.");
    	}
    	
    	List<Book> booksList = booksService.getAllBooksList();
    	
    	long id = booksList.isEmpty() ? 1 : booksList.get(booksList.size() - 1).getId() + 1;
    	
    	Book newBook = new Book(id, bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getPublished());
    			
		booksService.addBook(newBook);
	}
	
    @ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/books/{id}")
	public void deleteBook(@PathVariable("id") @Min(value = 1) long id) {
    	
    	booksService.findBookById(id);
    	
		booksService.deleteBook(id);
	}
    
	public boolean isAlphaNumeric(String s){
	    String pattern= "^[a-zA-Z0-9]*$";
	    return s.matches(pattern);
	}
  

}
