package com.stanleyyeung.crudrestapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stanleyyeung.crudrestapi.request.BookRequest;
import com.stanleyyeung.crudrestapi.service.BooksService;

@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class BookControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private BooksService booksServiceMock;
	
	@Test
	public void getBooksHttpRequestSuccess() throws Exception {
	    
	    mockMvc.perform(MockMvcRequestBuilders.get("/books"))
	    		.andExpect(MockMvcResultMatchers.status().isOk())
	    		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	    		.andReturn();
	    
	}
	
	@Test
	public void getBooksByAuthorHttpRequestSuccess() throws Exception {
		
		MockHttpServletRequestBuilder requestBuilder = get("/books")
                						.param("author", "stanley");
		
	    mockMvc.perform(requestBuilder)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andReturn();
		
	}
	
	@Test
	public void getBooksByAuthorHttpRequestFail() throws Exception {
		
		MockHttpServletRequestBuilder requestBuilder = get("/books")
                						.param("author", "test");
		
	    mockMvc.perform(requestBuilder)
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andReturn();
		
	}
	
	@Test
	public void getBooksByStatusHttpRequestSuccess() throws Exception {
		
		MockHttpServletRequestBuilder requestBuilder = get("/books")
                						.param("status", "true");
		
	    mockMvc.perform(requestBuilder)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andReturn();
		
	}
	
	@Test
	public void getBooksByStatusHttpRequestFail() throws Exception {
		
		MockHttpServletRequestBuilder requestBuilder = get("/books")
                						.param("status", "123");
		
	    mockMvc.perform(requestBuilder)
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andReturn();
		
	}
	
	@Test
	public void getBooksByAuthorAndStatusHttpRequestSuccess() throws Exception {
		
		MockHttpServletRequestBuilder requestBuilder = get("/books")
                						.param("author", "stanley")
                						.param("status", "true");
		
	    mockMvc.perform(requestBuilder)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andReturn();
		
	}
	
	@Test
	public void addBookHttpRequestSuccess() throws Exception {
		
		BookRequest bookRequest = new BookRequest("storybook", "Stan", true);
		ObjectMapper objectMapper = new ObjectMapper();
		
	    mockMvc.perform(post("/books")
	    .contentType(MediaType.APPLICATION_JSON)
	    .content(objectMapper.writeValueAsString(bookRequest)))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andReturn();
		
	}
	
	@Test
	public void addBookHttpRequestFail() throws Exception {
		
		BookRequest bookRequest = new BookRequest("-`?-", "-`?-", true);
		ObjectMapper objectMapper = new ObjectMapper();
		
	    mockMvc.perform(post("/books")
	    .contentType(MediaType.APPLICATION_JSON)
	    .content(objectMapper.writeValueAsString(bookRequest)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andReturn();
		
	}
	
	@Test
	public void deleteBookHttpRequestSuccess() throws Exception {
		
		MockHttpServletRequestBuilder requestBuilder = delete("/books/2");
		
	    mockMvc.perform(requestBuilder)
		.andExpect(MockMvcResultMatchers.status().isNoContent())
		.andReturn();
		
	}
	
	@Test
	public void deleteBookHttpRequestFail() throws Exception {
		
		MockHttpServletRequestBuilder requestBuilder = delete("/books/9");
		
	    mockMvc.perform(requestBuilder)
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andReturn();
		
	}
	

}
