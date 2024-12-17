package com.stadtbucheri.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stadtbucheri.DateUtils;
import com.stadtbucheri.entity.AuthorEntity;
import com.stadtbucheri.repository.AuthorRepository;

@Service
@Transactional(readOnly = true)
public class AuthorServiceImpl implements AuthorService {

	private final AuthorRepository authorRepository;

	public AuthorServiceImpl(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	@Transactional
	@Override
	public Long createAuthor(String name, String dateOfBirth) {
		AuthorEntity author = new AuthorEntity();
		author.setName(name);
		author.setDateOfBirth(DateUtils.convertStringToDate(dateOfBirth));
		AuthorEntity savedAuthor = authorRepository.save(author);
		return savedAuthor.getId();
	}

	@Override
	public List<AuthorEntity> getAllAuthors() {
		return authorRepository.findAll();
	}

	@Override
	public AuthorEntity getAuthorById(Long id) {
		return authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
	}

	@Transactional
	@Override
	public void deleteAuthor(Long id) {
		authorRepository.deleteById(id);
	}
}
