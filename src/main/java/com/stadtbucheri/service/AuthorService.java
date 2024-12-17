package com.stadtbucheri.service;

import java.util.List;

import com.stadtbucheri.entity.AuthorEntity;

public interface AuthorService {

	Long createAuthor(String name, String dateOfBirth);

	List<AuthorEntity> getAllAuthors();

	AuthorEntity getAuthorById(Long id);

	void deleteAuthor(Long id);
}
