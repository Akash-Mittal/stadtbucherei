package com.stadtbucheri.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stadtbucheri.entity.MemberEntity;
import com.stadtbucheri.repository.MemberRepository;

@Service
public class MemberService {

	private final MemberRepository memberRepository;

	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public MemberEntity createMember(MemberEntity member) {
		return memberRepository.save(member);
	}

	public Optional<MemberEntity> getMemberById(Long id) {
		return memberRepository.findById(id);
	}

	public List<MemberEntity> getAllMembers() {
		return memberRepository.findAll();
	}

	public MemberEntity updateMember(Long id, MemberEntity updatedMember) {
		return memberRepository.findById(id).map(existingMember -> {
			existingMember.setUsername(updatedMember.getUsername());
			existingMember.setEmail(updatedMember.getEmail());
			existingMember.setAddress(updatedMember.getAddress());
			existingMember.setPhoneNumber(updatedMember.getPhoneNumber());
			return memberRepository.save(existingMember);
		}).orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + id));
	}

	public void deleteMember(Long id) {
		if (memberRepository.existsById(id)) {
			memberRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("Member not found with id: " + id);
		}
	}
}
