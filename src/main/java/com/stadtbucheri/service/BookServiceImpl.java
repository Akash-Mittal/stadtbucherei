package com.stadtbucheri.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stadtbucheri.entity.AuthorEntity;
import com.stadtbucheri.entity.BookEntity;
import com.stadtbucheri.repository.AuthorRepository;
import com.stadtbucheri.repository.BookRepository;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;

	public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
	}

	@Transactional
	@Override
	public Long createBook(String title, String genre, double price, Long authorId) {
		AuthorEntity author = authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException("Author not found"));

		BookEntity book = new BookEntity();
		book.setTitle(title);
		book.setGenre(genre);
		book.setPrice(price);
		book.setAuthor(author);

		BookEntity savedBook = bookRepository.save(book);
		return savedBook.getId();
	}

	@Override
	public List<BookEntity> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public BookEntity getBookById(Long id) {
		return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
	}

	@Transactional
	@Override
	public void deleteBook(Long id) {
		bookRepository.deleteById(id);
	}
}
