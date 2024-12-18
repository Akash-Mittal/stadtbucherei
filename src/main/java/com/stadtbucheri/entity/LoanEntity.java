package com.stadtbucheri.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "loan", uniqueConstraints = @UniqueConstraint(columnNames = { "title", "genre", "price" }))

public class LoanEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private MemberEntity member;

	@ManyToOne
	@JoinColumn(name = "book_id", nullable = false)
	private BookEntity book;

	private LocalDate lendDate;

	private LocalDate returnDate;

}
