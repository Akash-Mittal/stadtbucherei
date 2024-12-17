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

import com.stadtbucheri.dto.AuthorData;
import com.stadtbucheri.dto.CreateAuthorRequest;
import com.stadtbucheri.entity.AuthorEntity;
import com.stadtbucheri.service.AuthorService;

@RestController
@RequestMapping("/services/authors")
public class AuthorController {

	private final AuthorService authorService;

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@PostMapping
	public ResponseEntity<Long> createAuthor(@RequestBody CreateAuthorRequest request) {
		Long authorId = authorService.createAuthor(request.getName(), request.getDateOfBirth());
		return ResponseEntity.ok(authorId);
	}

	@GetMapping
	public ResponseEntity<List<AuthorData>> getAllAuthors() {
		List<AuthorEntity> authors = authorService.getAllAuthors();
		List<AuthorData> response = authors.stream()
				.map(author -> new AuthorData(author.getId(), author.getName(), author.getDateOfBirth().toString()))
				.collect(Collectors.toList());
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
		authorService.deleteAuthor(id);
		return ResponseEntity.ok().build();
	}
}
