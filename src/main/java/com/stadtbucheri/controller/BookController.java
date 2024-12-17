package com.stadtbucheri.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stadtbucheri.dto.BookData;
import com.stadtbucheri.dto.CreateBookRequest;
import com.stadtbucheri.entity.BookEntity;
import com.stadtbucheri.service.BookService;

@RestController
@RequestMapping("/services/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping
	public ResponseEntity<Long> createBook(@RequestBody CreateBookRequest request) {
		Long bookId = bookService.createBook(request.getTitle(), request.getGenre(), request.getPrice(),
				request.getAuthorId());
		return ResponseEntity.ok(bookId);
	}

	@GetMapping
	public ResponseEntity<List<BookData>> getAllBooks() {
		List<BookEntity> books = bookService.getAllBooks();
		List<BookData> response = books.stream().map(book -> new BookData(book.getId(), book.getTitle(),
				book.getGenre(), book.getPrice(), book.getAuthor().getId())).collect(Collectors.toList());
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		return ResponseEntity.ok().build();
	}
}
