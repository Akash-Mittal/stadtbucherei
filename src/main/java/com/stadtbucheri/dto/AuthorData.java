package com.stadtbucheri.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorData {
	private Long id;
	private String name;
	private String dateOfBirth;
}
