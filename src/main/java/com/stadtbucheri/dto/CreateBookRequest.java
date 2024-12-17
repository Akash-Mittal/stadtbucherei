package com.stadtbucheri.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequest {
	private String title;
	private String genre;
	private double price;
	private Long authorId;
}
