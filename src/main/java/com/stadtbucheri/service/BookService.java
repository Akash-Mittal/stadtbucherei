package com.stadtbucheri.service;

import java.util.List;

import com.stadtbucheri.entity.BookEntity;

public interface BookService {

	Long createBook(String title, String genre, double price, Long authorId);

	List<BookEntity> getAllBooks();

	BookEntity getBookById(Long id);

	void deleteBook(Long id);
}
