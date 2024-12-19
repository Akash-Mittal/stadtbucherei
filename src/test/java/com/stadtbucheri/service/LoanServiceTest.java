package com.stadtbucheri.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.stadtbucheri.entity.AuthorEntity;
import com.stadtbucheri.entity.BookEntity;
import com.stadtbucheri.entity.LoanEntity;
import com.stadtbucheri.entity.MemberEntity;
import com.stadtbucheri.repository.LoanRepository;
import com.stadtbucheri.utils.DateUtils;
import com.stadtbucheri.utils.SampleDataUtils;

@SpringBootTest
@ActiveProfiles("h2") // Use H2 in-memory DB for testing

class LoanServiceTest {

	@Autowired
	LoanService loanService;

	@Autowired
	MemberService memberService;

	@Autowired
	AuthorService authorService;

	@Autowired
	BookService bookService;
	@Autowired
	LoanRepository loanRepository;

	private MemberEntity member;
	private BookEntity book;
	private AuthorEntity author;

	@BeforeEach
	void setUp() {
		loanRepository.deleteAll();
		// Create sample member and book
		member = SampleDataUtils.getSampleMember();
		memberService.createMember(member);

		author = SampleDataUtils.getSampleAuthor();
		Long authorID = authorService.createAuthor(author.getName(),
				DateUtils.convertDateToString(author.getDateOfBirth()));
		author = authorService.getAuthorById(authorID);
		book = SampleDataUtils.getSampleBook(author);
		Long bookID = bookService.createBook(book.getTitle(), book.getGenre(), book.getPrice(),
				book.getAuthor().getId());
		book = bookService.getBookById(bookID);
	}

	@Test
	void testCreateAndRetrieveLoan() {
		// Create loan
		LoanEntity loan = SampleDataUtils.getSampleLoan(member, book);
		loan = loanService.createLoan(loan);

		// Verify loan creation
		assertNotNull(loan.getId());
		assertEquals(member.getId(), loan.getMember().getId());
		assertEquals(book.getId(), loan.getBook().getId());
		assertEquals(LocalDate.now(), loan.getLendDate());

		// Retrieve loan by ID
		Optional<LoanEntity> retrievedLoan = loanService.getLoanById(loan.getId());
		assertTrue(retrievedLoan.isPresent());
		assertEquals(loan.getId(), retrievedLoan.get().getId());
	}

	@Test
	void testUpdateLoan() {
		// Create loan
		LoanEntity loan = SampleDataUtils.getSampleLoan(member, book);
		loan = loanService.createLoan(loan);

		// Update loan return date
		LocalDate newReturnDate = LocalDate.now().plusDays(10);
		loan.setReturnDate(newReturnDate);
		loan = loanService.updateLoan(loan.getId(), loan);

		// Verify updated return date
		assertNotNull(loan.getReturnDate());
		assertEquals(newReturnDate, loan.getReturnDate());
	}

	@Test
	void testGetLoansByMemberId() {
		// Create loan
		LoanEntity loan1 = SampleDataUtils.getSampleLoan(member, book);
		loanService.createLoan(loan1);

		// Create another loan
		LoanEntity loan2 = SampleDataUtils.getSampleLoan(member, book);
		loanService.createLoan(loan2);

		// Retrieve loans by member ID
		List<LoanEntity> loans = loanService.getLoansByMemberId(member.getId());
		assertEquals(2, loans.size());
	}

	@Test
	void testDeleteLoan() {
		// Create loan
		LoanEntity loan = SampleDataUtils.getSampleLoan(member, book);
		loan = loanService.createLoan(loan);
		loanService.deleteLoan(loan.getId());
		Optional<LoanEntity> deletedLoan = loanService.getLoanById(loan.getId());
		assertTrue(deletedLoan.isEmpty());
	}
}
