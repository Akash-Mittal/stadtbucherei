package com.stadtbucheri.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stadtbucheri.entity.LoanEntity;
import com.stadtbucheri.service.LoanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/loans")
@Tag(name = "Loan Management", description = "Operations related to loan management in the library")
public class LoanController {

	@Autowired
	private LoanService loanService;

	// Create a loan
	@Operation(summary = "Create a new loan", description = "Creates a new loan for a member and a book.", responses = {
			@ApiResponse(responseCode = "200", description = "Loan created successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request") })
	@PostMapping
	public LoanEntity createLoan(@RequestBody LoanEntity loanEntity) {
		return loanService.createLoan(loanEntity);
	}

	@Operation(summary = "Get loan by ID", description = "Fetches a loan by its ID.", responses = {
			@ApiResponse(responseCode = "200", description = "Loan found"),
			@ApiResponse(responseCode = "404", description = "Loan not found") })
	@GetMapping("/{id}")
	public Optional<LoanEntity> getLoanById(
			@Parameter(description = "ID of the loan to be fetched") @PathVariable Long id) {
		return loanService.getLoanById(id);
	}

	@Operation(summary = "Get loans by member ID", description = "Fetches all loans made by a specific member.", responses = {
			@ApiResponse(responseCode = "200", description = "List of loans found"),
			@ApiResponse(responseCode = "404", description = "No loans found for member") })
	@GetMapping("/member/{memberId}")
	public List<LoanEntity> getLoansByMemberId(
			@Parameter(description = "ID of the member whose loans are to be fetched") @PathVariable Long memberId) {
		return loanService.getLoansByMemberId(memberId);
	}

	@Operation(summary = "Update an existing loan", description = "Updates the details of an existing loan.", responses = {
			@ApiResponse(responseCode = "200", description = "Loan updated successfully"),
			@ApiResponse(responseCode = "404", description = "Loan not found") })
	@PutMapping("/{id}")
	public LoanEntity updateLoan(@Parameter(description = "ID of the loan to be updated") @PathVariable Long id,
			@RequestBody LoanEntity loanEntity) {
		return loanService.updateLoan(id, loanEntity);
	}

	@Operation(summary = "Delete a loan", description = "Deletes an existing loan by its ID.", responses = {
			@ApiResponse(responseCode = "200", description = "Loan deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Loan not found") })
	@DeleteMapping("/{id}")
	public void deleteLoan(@Parameter(description = "ID of the loan to be deleted") @PathVariable Long id) {
		loanService.deleteLoan(id);
	}

	@Operation(summary = "Get loans after a specific date", description = "Fetches all loans that were made after a specific date.", responses = {
			@ApiResponse(responseCode = "200", description = "List of loans found"),
			@ApiResponse(responseCode = "400", description = "Invalid date format") })
	@GetMapping("/after/{date}")
	public List<LoanEntity> getLoansAfterDate(
			@Parameter(description = "Date after which the loans are to be fetched (in YYYY-MM-DD format)") @PathVariable String date) {
		return loanService.getLoansAfterDate(LocalDate.parse(date));
	}
}
