package com.stadtbucheri.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.stadtbucheri.entity.AuthorEntity;
import com.stadtbucheri.repository.AuthorRepository;
import com.stadtbucheri.utils.DateUtils;

@SpringBootTest
@ActiveProfiles("h2")
class AuthorServiceTest {

	@Autowired
	AuthorService authorService;

	@Autowired
	AuthorRepository authorRepository;

	@BeforeEach
	void cleanDatabase() {
		authorRepository.deleteAll();
	}

	@Test
	void testAuthorService() {
		// Verify the service starts with no authors
		assertEquals(0, authorService.getAllAuthors().size());

		// Create an author using a String for the date of birth
		Long id = authorService.createAuthor("Jane Austen", "16.12.1990");
		assertNotNull(id);

		// Verify the author was created
		Collection<AuthorEntity> authors = authorService.getAllAuthors();
		assertEquals(1, authors.size());

		Optional<AuthorEntity> author = authors.stream().findFirst();
		assertTrue(author.isPresent());
		assertEquals(id, author.get().getId());
		assertEquals("Jane Austen", author.get().getName());
		assertEquals(DateUtils.convertStringToDate("16.12.1990"), author.get().getDateOfBirth()); // Adjusting to String
																									// format

		// Delete the author
		authorService.deleteAuthor(Long.valueOf(id));
		assertEquals(0, authorService.getAllAuthors().size());
	}
}
