package com.stadtbucheri.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stadtbucheri.entity.LoanEntity;
import com.stadtbucheri.repository.LoanRepository;

@Service
@Transactional(readOnly = true)
public class LoanService {

	@Autowired
	private LoanRepository loanRepository;

	// Create a loan
	public LoanEntity createLoan(LoanEntity loanEntity) {
		loanEntity.setLendDate(LocalDate.now()); // Set the current date as the lend date
		return loanRepository.save(loanEntity);
	}

	// Retrieve a loan by ID
	public Optional<LoanEntity> getLoanById(Long id) {
		return loanRepository.findById(id);
	}

	// Retrieve loans by member ID
	public List<LoanEntity> getLoansByMemberId(Long memberId) {
		return loanRepository.findByMemberId(memberId);
	}

	// Update a loan (for example, return the book)
	public LoanEntity updateLoan(Long loanId, LoanEntity updatedLoan) {
		Optional<LoanEntity> existingLoanOpt = loanRepository.findById(loanId);
		if (existingLoanOpt.isPresent()) {
			LoanEntity existingLoan = existingLoanOpt.get();
			existingLoan.setReturnDate(updatedLoan.getReturnDate());
			return loanRepository.save(existingLoan);
		}
		return null;
	}

	// Delete a loan by ID
	public void deleteLoan(Long loanId) {
		loanRepository.deleteById(loanId);
	}

	// Find loans after a specific date (for example, overdue loans)
	public List<LoanEntity> getLoansAfterDate(LocalDate date) {
		return loanRepository.findByLendDateAfter(date);
	}

	// Check if a specific book is already loaned to a member
	public Optional<LoanEntity> checkIfBookIsLoanedToMember(Long memberId, Long bookId) {
		return loanRepository.findByMemberIdAndBookId(memberId, bookId);
	}
}
