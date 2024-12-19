package com.stadtbucheri.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
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
import com.stadtbucheri.exception.BusinessException;
import com.stadtbucheri.repository.AuthorRepository;
import com.stadtbucheri.repository.BookRepository;
import com.stadtbucheri.repository.LoanRepository;
import com.stadtbucheri.repository.MemberRepository;
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

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	AuthorRepository authorRepository;

	private MemberEntity member;
	private BookEntity book;
	private AuthorEntity author;

	@BeforeEach
	void setUp() {
		loanRepository.deleteAll();
		memberRepository.deleteAll();
		bookRepository.deleteAll();
		authorRepository.deleteAll();

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
	void testCreateLoanExceedsLimit() {
		// Arrange: Create and save a member
		MemberEntity member = SampleDataUtils.getSampleMember();
		member = memberRepository.save(member);

		// Create and save 5 active loans for the member
		List<LoanEntity> activeLoans = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			// Create and save an author
			AuthorEntity author = SampleDataUtils.getSampleAuthor();
			author = authorRepository.save(author);

			// Create and save a book associated with the saved author
			BookEntity book = SampleDataUtils.getSampleBook(author);
			book.setAuthor(author);
			book = bookRepository.save(book);

			// Create and save a loan with the saved book and member
			LoanEntity loan = new LoanEntity();
			loan.setMember(member);
			loan.setBook(book);
			loan.setLendDate(LocalDate.now().minusDays(i)); // Simulate different lend dates
			activeLoans.add(loanRepository.save(loan));
		}

		// Create a new book for the new loan
		AuthorEntity newAuthor = SampleDataUtils.getSampleAuthor();
		newAuthor = authorRepository.save(newAuthor);

		BookEntity newBook = SampleDataUtils.getSampleBook(newAuthor);
		newBook.setAuthor(newAuthor);
		newBook = bookRepository.save(newBook);

		// Act: Try to create a new loan exceeding the limit
		LoanEntity newLoan = new LoanEntity();
		newLoan.setMember(member); // Use the saved member
		newLoan.setBook(newBook); // Use the saved new book

		// Assert: Expect a BusinessException due to exceeding loan limit
		BusinessException exception = assertThrows(BusinessException.class, () -> loanService.createLoan(newLoan));
		assertNotNull(exception);
		assertEquals("The member has reached the maximum loan limit of 5 books.", exception.getMessage());
	}

	@Test
	void testCreateLoanWithNullMember() {
		// Arrange: Create a loan with null member
		LoanEntity loan = SampleDataUtils.getSampleLoan(null, book);

		// Assert: Expect a BusinessException due to null member
		BusinessException exception = assertThrows(BusinessException.class, () -> loanService.createLoan(loan));
		assertNotNull(exception);
		assertEquals("Loan must be associated with a valid member.", exception.getMessage());
	}

	@Test
	void testCreateLoanWithNullBook() {
		// Arrange: Create a loan with null book
		LoanEntity loan = SampleDataUtils.getSampleLoan(member, null);

		// Assert: Expect a BusinessException due to null book
		BusinessException exception = assertThrows(BusinessException.class, () -> loanService.createLoan(loan));
		assertNotNull(exception);
		assertEquals("Loan must be associated with a valid book.", exception.getMessage());
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

}
