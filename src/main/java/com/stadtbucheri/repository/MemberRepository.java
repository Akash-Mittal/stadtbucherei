package com.stadtbucheri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stadtbucheri.entity.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
	// Custom query methods can be defined here, if needed
	MemberEntity findByUsername(String username);
}
