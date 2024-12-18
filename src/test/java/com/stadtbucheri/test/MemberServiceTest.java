package com.stadtbucheri.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.stadtbucheri.entity.MemberEntity;
import com.stadtbucheri.service.MemberService;

@SpringBootTest
@ActiveProfiles("h2")
class MemberServiceTest {

	@Autowired
	MemberService memberService;

	@Test
	void testMemberService() {
		// Verify the service starts with no members
		assertEquals(0, memberService.getAllMembers().size());

		// Create a member
		MemberEntity newMember = new MemberEntity();
		newMember.setUsername("john_doe");
		newMember.setEmail("john.doe@example.com");
		newMember.setAddress("123 Main Street");
		newMember.setPhoneNumber("1234567890");
		MemberEntity savedMember = memberService.createMember(newMember);

		// Verify the member was created
		assertNotNull(savedMember.getId());
		assertEquals("john_doe", savedMember.getUsername());
		assertEquals("john.doe@example.com", savedMember.getEmail());
		assertEquals("123 Main Street", savedMember.getAddress());
		assertEquals("1234567890", savedMember.getPhoneNumber());

		// Verify the member is retrievable
		Collection<MemberEntity> members = memberService.getAllMembers();
		assertEquals(1, members.size());

		Optional<MemberEntity> retrievedMember = members.stream().findFirst();
		assertTrue(retrievedMember.isPresent());
		assertEquals(savedMember.getId(), retrievedMember.get().getId());
		assertEquals("john_doe", retrievedMember.get().getUsername());

		// Update the member
		savedMember.setAddress("456 Elm Street");
		MemberEntity updatedMember = memberService.updateMember(savedMember.getId(), savedMember);
		assertEquals("456 Elm Street", updatedMember.getAddress());

		// Delete the member
		memberService.deleteMember(savedMember.getId());
		assertEquals(0, memberService.getAllMembers().size());
	}

	@Test
	void testCreateAndRetrieveMember() {
		// Create a new member
		MemberEntity newMember = new MemberEntity();
		newMember.setUsername("jane_doe");
		newMember.setEmail("jane.doe@example.com");
		newMember.setAddress("789 Oak Avenue");
		newMember.setPhoneNumber("0987654321");
		MemberEntity savedMember = memberService.createMember(newMember);

		// Verify member was created
		assertNotNull(savedMember.getId());
		assertEquals("jane_doe", savedMember.getUsername());
		assertEquals("jane.doe@example.com", savedMember.getEmail());
		assertEquals("789 Oak Avenue", savedMember.getAddress());
		assertEquals("0987654321", savedMember.getPhoneNumber());

		// Verify the member is retrievable
		Collection<MemberEntity> members = memberService.getAllMembers();
		Optional<MemberEntity> retrievedMember = members.stream().findFirst();
		assertTrue(retrievedMember.isPresent());
		assertEquals(savedMember.getId(), retrievedMember.get().getId());
	}

	// Edge Case: Create Member with Missing Username
	@Test
	void testCreateMemberWithMissingData() {
		// Create a member with missing username
		MemberEntity incompleteMember = new MemberEntity();
		incompleteMember.setUsername(null); // Missing username
		incompleteMember.setEmail("missing.username@example.com");
		incompleteMember.setAddress("No address");
		incompleteMember.setPhoneNumber("1234567890");

		try {
			memberService.createMember(incompleteMember);
			fail("Expected exception due to missing username");
		} catch (Exception e) {
			// Assuming your service throws a specific exception for missing username
			assertTrue(e instanceof Exception);
		}
	}

	// Edge Case: Create Member with Invalid Email Format
	@Test
	void testCreateMemberWithInvalidEmail() {
		// Create a member with invalid email format
		MemberEntity invalidEmailMember = new MemberEntity();
		invalidEmailMember.setUsername("user_invalid_email");
		invalidEmailMember.setEmail("invalid-email-format"); // Invalid email
		invalidEmailMember.setAddress("Address");
		invalidEmailMember.setPhoneNumber("9876543210");

		try {
			memberService.createMember(invalidEmailMember);
			fail("Expected exception due to invalid email format");
		} catch (Exception e) {
			// Assuming your service throws a specific exception for invalid email
			assertTrue(e instanceof Exception);
		}
	}

	// Edge Case: Create Member with Empty Phone Number
	@Test
	void testCreateMemberWithEmptyPhoneNumber() {
		// Create a member with an empty phone number
		MemberEntity memberWithEmptyPhone = new MemberEntity();
		memberWithEmptyPhone.setUsername("user_no_phone");
		memberWithEmptyPhone.setEmail("user.no.phone@example.com");
		memberWithEmptyPhone.setAddress("No phone address");
		memberWithEmptyPhone.setPhoneNumber(""); // Empty phone number

		try {
			memberService.createMember(memberWithEmptyPhone);
			fail("Expected exception due to empty phone number");
		} catch (Exception e) {
			// Assuming your service throws a specific exception for empty phone number
			assertTrue(e instanceof Exception);
		}
	}

	// Happy Path: Update Member's Address
	@Test
	void testUpdateMemberAddress() {
		// Create a new member
		MemberEntity newMember = new MemberEntity();
		newMember.setUsername("update_user");
		newMember.setEmail("update.user@example.com");
		newMember.setAddress("123 Old Street");
		newMember.setPhoneNumber("1112223333");
		MemberEntity savedMember = memberService.createMember(newMember);

		// Update the member's address
		savedMember.setAddress("456 New Street");
		MemberEntity updatedMember = memberService.updateMember(savedMember.getId(), savedMember);

		// Verify the updated address
		assertEquals("456 New Street", updatedMember.getAddress());
	}

	// Edge Case: Create Member with Duplicate Username
	@Test
	void testCreateMemberWithDuplicateUsername() {
		// Create the first member with a username
		MemberEntity firstMember = new MemberEntity();
		firstMember.setUsername("unique_username");
		firstMember.setEmail("unique.username@example.com");
		firstMember.setAddress("123 Address");
		firstMember.setPhoneNumber("1231231234");
		memberService.createMember(firstMember);

		// Try to create a second member with the same username
		MemberEntity secondMember = new MemberEntity();
		secondMember.setUsername("unique_username"); // Duplicate username
		secondMember.setEmail("another.email@example.com");
		secondMember.setAddress("456 Address");
		secondMember.setPhoneNumber("9879879876");

		try {
			memberService.createMember(secondMember);
			fail("Expected exception due to duplicate username");
		} catch (Exception e) {
			// Assuming your service throws an exception for duplicate usernames
			assertTrue(e instanceof Exception);
		}
	}

	// Happy Path: Delete Member
	@Test
	void testDeleteMember() {
		// Create a new member
		MemberEntity newMember = new MemberEntity();
		newMember.setUsername("delete_user");
		newMember.setEmail("delete.user@example.com");
		newMember.setAddress("123 Delete Street");
		newMember.setPhoneNumber("3334445555");
		MemberEntity savedMember = memberService.createMember(newMember);

		// Delete the member
		memberService.deleteMember(savedMember.getId());

		// Verify the member is deleted
		Collection<MemberEntity> members = memberService.getAllMembers();
		assertEquals(0, members.size());
	}

	// Edge Case: Try Deleting a Non-Existent Member
	@Test
	void testDeleteNonExistentMember() {
		try {
			memberService.deleteMember(999L); // Non-existent ID
			fail("Expected exception for non-existent member deletion");
		} catch (Exception e) {
			// Assuming your service throws an exception for deleting a non-existent member
			assertTrue(e instanceof Exception);
		}
	}
}
