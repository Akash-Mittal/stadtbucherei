package com.stadtbucheri;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	public static String convertLocalDateToString(LocalDate date) {
		return date != null ? date.format(FORMATTER) : null;
	}

	public static Date convertStringToDate(String dateStr) {
		return Date.from(LocalDate.parse(dateStr, FORMATTER).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

	}
}