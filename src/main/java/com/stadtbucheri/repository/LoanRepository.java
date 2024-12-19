package com.stadtbucheri.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stadtbucheri.entity.LoanEntity;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

	List<LoanEntity> findByMemberId(Long memberId);

	Optional<LoanEntity> findByMemberIdAndBookId(Long memberId, Long bookId);

	List<LoanEntity> findByMemberIdAndReturnDateIsNull(Long memberId);

	List<LoanEntity> findByLendDateAfter(LocalDate date);
}
