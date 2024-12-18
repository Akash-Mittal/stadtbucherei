package com.stadtbucheri.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateAuthorRequest {
	private String name;
	private String dateOfBirth;
}
