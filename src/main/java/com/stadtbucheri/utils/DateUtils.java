package com.stadtbucheri.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	public static String convertLocalDateToString(LocalDate date) {
		return date != null ? date.format(FORMATTER) : null;
	}

	public static String convertDateToString(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy"); // Use desired format
		return formatter.format(date);
	}

	public static Date convertStringToDate(String dateStr) {
		return Date.from(LocalDate.parse(dateStr, FORMATTER).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

	}
}