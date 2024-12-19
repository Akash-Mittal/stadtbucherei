package com.stadtbucheri.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;

import com.stadtbucheri.entity.AuthorEntity;
import com.stadtbucheri.entity.BookEntity;
import com.stadtbucheri.entity.LoanEntity;
import com.stadtbucheri.entity.MemberEntity;

public class SampleDataUtils {

	// Sample data for authors (Top German authors)
	private static final String[] AUTHORS = { "Johann Wolfgang von Goethe", "Franz Kafka", "Thomas Mann",
			"Hermann Hesse", "Günter Grass", "Albert Einstein", "Heinrich Böll", "Berthold Brecht" };

	// Sample data for books (NYT Bestsellers)
	private static final String[] BOOKS = { "Where the Crawdads Sing", "The Midnight Library", "The Silent Patient",
			"The Seven Husbands of Evelyn Hugo", "Educated", "The Song of Achilles", "Circe", "The Night Circus" };

	private static final String[] GENRES = { "Fiction", "Mystery", "Romance", "Science Fiction", "Non-fiction" };

	private static final String[] ADDRESSES = { "1234 Elm St, Springfield", "4321 Oak St, Rivertown",
			"5678 Pine St, Lakedale", "1357 Maple St, Greenview" };

	private static final String[] PHONE_NUMBERS = { "123-456-7890", "987-654-3210", "555-1234-5678", "555-8765-4321" };

	private static Random random = new Random();

	// Utility method to generate sample Author
	public static AuthorEntity getSampleAuthor() {
		AuthorEntity author = new AuthorEntity();
		author.setName(AUTHORS[random.nextInt(AUTHORS.length)]);
		try {
			author.setDateOfBirth(generateRandomDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return author;
	}

	// Utility method to generate sample Book
	public static BookEntity getSampleBook(AuthorEntity author) {
		BookEntity book = new BookEntity();
		book.setTitle(BOOKS[random.nextInt(BOOKS.length)]);
		book.setGenre(GENRES[random.nextInt(GENRES.length)]);
		book.setPrice(generateRandomPrice());
		book.setAuthor(author);
		return book;
	}

	// Utility method to generate sample Member
	public static MemberEntity getSampleMember() {
		MemberEntity member = new MemberEntity();
		member.setUsername(generateRandomUsername());
		member.setEmail(generateRandomEmail());
		member.setAddress(ADDRESSES[random.nextInt(ADDRESSES.length)]);
		member.setPhoneNumber(PHONE_NUMBERS[random.nextInt(PHONE_NUMBERS.length)]);
		return member;
	}

	// Utility method to generate sample Loan with LocalDate
	public static LoanEntity getSampleLoan(MemberEntity member, BookEntity book) {
		LoanEntity loan = new LoanEntity();
		loan.setMember(member);
		loan.setBook(book);

		// Convert Date to LocalDate
		try {
			loan.setLendDate(convertDateToLocalDate(generateRandomDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loan.setReturnDate((generateRandomDateAfter(loan.getLendDate())));

		return loan;
	}

	// Convert Date to LocalDate
	private static LocalDate convertDateToLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	// Generate a random date
	private static Date generateRandomDate() throws ParseException {
		long minDate = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.1950").getTime();
		long maxDate = new Date().getTime();
		long randomTime = minDate + (long) (Math.random() * (maxDate - minDate));
		return new Date(randomTime);
	}

	// Generate a random price between 10 and 100
	private static double generateRandomPrice() {
		return 10 + (Math.random() * 90); // Random price between 10 and 100
	}

	// Generate a random username
	private static String generateRandomUsername() {
		return "user" + random.nextInt(10000);
	}

	// Generate a random email address
	private static String generateRandomEmail() {
		return "email" + random.nextInt(10000) + "@example.com";
	}

	// Generate a random return date (LocalDate) after a given lend date
	private static LocalDate generateRandomDateAfter(LocalDate lendDate) {
		// Generate a random number of days (up to 30 days after lend date)
		long randomDays = random.nextInt(30) + 1; // Up to 30 days

		// Add the random number of days to the lend date
		return lendDate.plus(randomDays, ChronoUnit.DAYS);
	}

}
