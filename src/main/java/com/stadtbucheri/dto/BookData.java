package com.stadtbucheri.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookData {
	private Long id;
	private String title;
	private String genre;
	private double price;
	private Long authorId;
}
