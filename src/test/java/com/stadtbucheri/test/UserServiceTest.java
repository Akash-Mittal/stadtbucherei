//package com.stadtbucheri.test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.Collection;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import com.stadtbucheri.entity.UserEntity;
//import com.stadtbucheri.service.UserService;
//
//@SpringBootTest
//@ActiveProfiles("h2")
//class UserServiceTest {
//
//	@Autowired
//	UserService userService;
//
//	@Test
//	void testUserService() {
//		assertEquals(0, userService.getAll().size());
//		String id = userService.createUser("john", "doe");
//		assertNotNull(id);
//
//		Collection<UserEntity> users = userService.getAll();
//		assertEquals(1, users.size());
//
//		Optional<UserEntity> user = users.stream().findFirst();
//		assertTrue(user.isPresent());
//		assertEquals(id, user.get().getId());
//		assertEquals("john", user.get().getName());
//		assertEquals("doe", user.get().getEmail());
//
//		userService.deleteUser(id);
//		assertEquals(0, userService.getAll().size());
//	}
//
//}
