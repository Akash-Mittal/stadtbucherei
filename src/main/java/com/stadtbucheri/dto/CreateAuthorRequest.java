package com.stadtbucheri.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAuthorRequest {
	private String name;
	private String dateOfBirth;
}
