package com.stadtbucheri.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.stadtbucheri.entity.AuthorEntity;
import com.stadtbucheri.entity.BookEntity;

@SpringBootTest
@ActiveProfiles("h2")
class BookServiceTest {

	@Autowired
	BookService bookService;

	@Autowired
	AuthorService authorService;

	@Test
	void testCreateAndRetrieveBook() {

		Long authorId = authorService.createAuthor("Steve Jabrosky", "15.05.2021");
		assertNotNull(authorId);

		AuthorEntity author = authorService.getAuthorById(authorId);
		assertNotNull(author);

		BookEntity book = new BookEntity();
		book.setTitle("Sample Book Title");
		book.setGenre("Fiction");
		book.setPrice(19.99);
		book.setAuthor(author); //

		Long bookId = bookService.createBook(book.getTitle(), book.getGenre(), book.getPrice(), author.getId());
		assertNotNull(bookId);

		BookEntity retrievedBook = bookService.getBookById(bookId);
		assertNotNull(retrievedBook);
		assertEquals(bookId, retrievedBook.getId());
	}

}
