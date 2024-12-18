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

@RestController
@RequestMapping("/api/loans")
public class LoanController {

	@Autowired
	private LoanService loanService;

	// Create a loan
	@PostMapping
	public LoanEntity createLoan(@RequestBody LoanEntity loanEntity) {
		return loanService.createLoan(loanEntity);
	}

	// Get loan by ID
	@GetMapping("/{id}")
	public Optional<LoanEntity> getLoanById(@PathVariable Long id) {
		return loanService.getLoanById(id);
	}

	// Get loans by member ID
	@GetMapping("/member/{memberId}")
	public List<LoanEntity> getLoansByMemberId(@PathVariable Long memberId) {
		return loanService.getLoansByMemberId(memberId);
	}

	// Update a loan
	@PutMapping("/{id}")
	public LoanEntity updateLoan(@PathVariable Long id, @RequestBody LoanEntity loanEntity) {
		return loanService.updateLoan(id, loanEntity);
	}

	// Delete a loan
	@DeleteMapping("/{id}")
	public void deleteLoan(@PathVariable Long id) {
		loanService.deleteLoan(id);
	}

	// Get loans after a specific date
	@GetMapping("/after/{date}")
	public List<LoanEntity> getLoansAfterDate(@PathVariable String date) {
		return loanService.getLoansAfterDate(LocalDate.parse(date));
	}
}
