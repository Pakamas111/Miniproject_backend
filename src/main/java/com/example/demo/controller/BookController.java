package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Admin;
import com.example.demo.model.Book;
import com.example.demo.model.Course;
import com.example.demo.model.Trainer;
import com.example.demo.repository.BookRepository;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
public class BookController {

	@Autowired
	BookRepository bookRepository;

	@GetMapping("/book")
	public ResponseEntity<Object> getbook() {
		try {
			List<Book> books = bookRepository.findAll();
			return new ResponseEntity<>(books, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>("Internal server error", HttpStatus.OK);
			// TODO: handle exception
		}
	}

	@PostMapping("/book")
	public ResponseEntity<Object> addBook(@RequestBody Book body) {
		try {
			bookRepository.save(body);

			return new ResponseEntity<>(body, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Integer server  error", HttpStatus.OK);
		}
	}

	@GetMapping("/book/{bookId}")
	public ResponseEntity<Object> getbookDetail(@PathVariable Integer bookId) {
		try {

			Optional<Book> book = bookRepository.findById(bookId);

			if (book.isPresent()) {
				return new ResponseEntity<>(book, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Book not found", HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server  error", HttpStatus.OK);
		}
	}

	@PutMapping("/book/{bookId}")
	public ResponseEntity<Object> updatBook(@PathVariable Integer bookId, @RequestBody Book body) {
		try {
			Optional<Book> book = bookRepository.findById(bookId);

			if (book.isPresent()) {

				Book bookEdit = book.get();

				bookEdit.setBookDate(body.getBookDate());
				bookEdit.setUser(body.getUser());
				bookRepository.save(bookEdit);

				return new ResponseEntity<>("put SUCSESS", HttpStatus.OK);

			} else {

				return new ResponseEntity<>("book Not found", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@DeleteMapping("/book/{bookId}")
	public ResponseEntity<Object> deletBook(@PathVariable Integer bookId) {

		try {

			Optional<Book> book = bookRepository.findById(bookId);

			if (book.isPresent()) {
				bookRepository.delete(book.get());

				return new ResponseEntity<>("DELETE SUCSESS", HttpStatus.OK);

			} else {

				return new ResponseEntity<>("book not found", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {

			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
