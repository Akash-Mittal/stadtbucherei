package com.stadtbucheri.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stadtbucheri.entity.LoanEntity;
import com.stadtbucheri.exception.BusinessException;
import com.stadtbucheri.repository.LoanRepository;

@Service
@Transactional(readOnly = true)
public class LoanService {

	@Autowired
	private LoanRepository loanRepository;

	@Transactional
	public LoanEntity createLoan(LoanEntity loanEntity) {
		// Null check for loanEntity
		if (loanEntity == null) {
			throw new BusinessException("Loan entity cannot be null.", HttpStatus.BAD_REQUEST);
		}

		// Null check for required fields in loanEntity
		if (loanEntity.getMember() == null || loanEntity.getMember().getId() == null) {
			throw new BusinessException("Loan must be associated with a valid member.", HttpStatus.BAD_REQUEST);
		}
		if (loanEntity.getBook() == null || loanEntity.getBook().getId() == null) {
			throw new BusinessException("Loan must be associated with a valid book.", HttpStatus.BAD_REQUEST);
		}

		// Check if the member has reached the loan limit
		if (!checkLoanLimit(loanEntity.getMember().getId())) {
			throw new BusinessException("The member has reached the maximum loan limit of 5 books.",
					HttpStatus.BAD_REQUEST);
		}

		loanEntity.setLendDate(LocalDate.now());
		return loanRepository.save(loanEntity);
	}

	public Optional<LoanEntity> getLoanById(Long id) {
		if (id == null) {
			throw new BusinessException("Loan ID cannot be null.", HttpStatus.BAD_REQUEST);
		}
		return loanRepository.findById(id);
	}

	// Retrieve loans by member ID
	public List<LoanEntity> getLoansByMemberId(Long memberId) {
		if (memberId == null) {
			throw new BusinessException("Member ID cannot be null.", HttpStatus.BAD_REQUEST);
		}
		return loanRepository.findByMemberId(memberId);
	}

	// Update a loan (for example, return the book)
	public LoanEntity updateLoan(Long loanId, LoanEntity updatedLoan) {
		if (loanId == null || updatedLoan == null) {
			throw new BusinessException("Loan ID and updated loan entity cannot be null.", HttpStatus.BAD_REQUEST);
		}

		Optional<LoanEntity> existingLoanOpt = loanRepository.findById(loanId);
		if (existingLoanOpt.isPresent()) {
			LoanEntity existingLoan = existingLoanOpt.get();
			if (updatedLoan.getReturnDate() == null) {
				throw new BusinessException("Return date must be provided.", HttpStatus.BAD_REQUEST);
			}
			existingLoan.setReturnDate(updatedLoan.getReturnDate());
			return loanRepository.save(existingLoan);
		}
		throw new BusinessException("Loan not found with ID: " + loanId, HttpStatus.NOT_FOUND);
	}

	// Delete a loan by ID
	public void deleteLoan(Long loanId) {
		if (loanId == null) {
			throw new BusinessException("Loan ID cannot be null.", HttpStatus.BAD_REQUEST);
		}
		loanRepository.deleteById(loanId);
	}

	// Find loans after a specific date (for example, overdue loans)
	public List<LoanEntity> getLoansAfterDate(LocalDate date) {
		if (date == null) {
			throw new BusinessException("Date cannot be null.", HttpStatus.BAD_REQUEST);
		}
		return loanRepository.findByLendDateAfter(date);
	}

	// Check if a specific book is already loaned to a member
	public Optional<LoanEntity> checkIfBookIsLoanedToMember(Long memberId, Long bookId) {
		if (memberId == null || bookId == null) {
			throw new BusinessException("Member ID and Book ID cannot be null.", HttpStatus.BAD_REQUEST);
		}
		return loanRepository.findByMemberIdAndBookId(memberId, bookId);
	}

	// Check if a member has reached the loan limit
	public boolean checkLoanLimit(Long memberId) {
		if (memberId == null) {
			throw new BusinessException("Member ID cannot be null.", HttpStatus.BAD_REQUEST);
		}

		// Get all active loans for the member
		List<LoanEntity> activeLoans = loanRepository.findByMemberIdAndReturnDateIsNull(memberId);
		// Check if the count exceeds the limit of 5 books
		return activeLoans.size() < 5;
	}
}
