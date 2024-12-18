package com.stadtbucheri.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Date;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

@Ignore
class DateUtilsTest {

	// Test the conversion from LocalDate to String
	@Test
	void testConvertLocalDateToString() {
		LocalDate localDate = LocalDate.of(2024, 12, 17); // A normal date
		String result = DateUtils.convertLocalDateToString(localDate);
		assertEquals("17.12.2024", result);
	}

	// Test the conversion from String to Date
	@Test
	void testConvertStringToDate() {
		String dateStr = "17.12.2024"; // A normal date string
		Date result = DateUtils.convertStringToDate(dateStr);
		assertNotNull(result);
	}

	// Edge case: Test empty string input
	@Test
	void testConvertEmptyStringToDate() {
		String dateStr = ""; // Empty string should be invalid
		assertThrows(java.time.format.DateTimeParseException.class, () -> {
			DateUtils.convertStringToDate(dateStr);
		});
	}

	// Edge case: Test invalid date string format
	@Test
	void testConvertInvalidDateStringToDate() {
		String dateStr = "2024-12-17"; // Invalid format (should be dd.MM.yyyy)
		assertThrows(java.time.format.DateTimeParseException.class, () -> {
			DateUtils.convertStringToDate(dateStr);
		});
	}

}
