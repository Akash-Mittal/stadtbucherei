package com.stadtbucheri.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;

import com.stadtbucheri.dto.AuthorData;
import com.stadtbucheri.dto.CreateAuthorRequest;

@Disabled("Controller tests are temporarily disabled as its impacting system performance due to dependency on docker engine")
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = AuthorControllerTest.Initializer.class)
class AuthorControllerTest {

	private static Long firstAuthorId;
	private static Long secondAuthorId;

	@LocalServerPort
	int port;

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	@Order(1)
	void getAuthors() {
		ResponseEntity<AuthorData[]> responseEntity = restTemplate.getForEntity("/services/authors",
				AuthorData[].class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(0, responseEntity.getBody().length);
	}

	@Test
	@Order(2)
	void createFirstAuthor() {
		CreateAuthorRequest request = new CreateAuthorRequest("author-name-01", "01.01.1980");
		ResponseEntity<Map> response = restTemplate.postForEntity("/services/authors", request, Map.class);
		firstAuthorId = ((Number) response.getBody().get("id")).longValue();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(firstAuthorId);
	}

	@Test
	@Order(3)
	void checkAuthorsAfterFirstCreation() {
		ResponseEntity<AuthorData[]> responseEntity = restTemplate.getForEntity("/services/authors",
				AuthorData[].class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(1, responseEntity.getBody().length);
	}

	@Test
	@Order(4)
	void createSecondAuthor() {
		CreateAuthorRequest request = new CreateAuthorRequest("author-name-02", "02.02.1990");
		ResponseEntity<Long> responseEntity = restTemplate.postForEntity("/services/authors", request, Long.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		secondAuthorId = responseEntity.getBody();
		assertNotNull(secondAuthorId);
	}

	@Test
	@Order(5)
	void deleteFirstAuthor() {
		restTemplate.delete("/services/authors/" + firstAuthorId);
		// Check if the author is deleted
		ResponseEntity<AuthorData[]> responseEntity = restTemplate.getForEntity("/services/authors",
				AuthorData[].class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(1, responseEntity.getBody().length);
	}

	@Test
	@Order(6)
	void deleteSecondAuthor() {
		restTemplate.delete("/services/authors/" + secondAuthorId);
		// Check if the author is deleted
		ResponseEntity<AuthorData[]> responseEntity = restTemplate.getForEntity("/services/authors",
				AuthorData[].class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(0, responseEntity.getBody().length);
	}

	@Test
	@Order(7)
	void testOptions() {
		Set<HttpMethod> httpMethods = restTemplate.optionsForAllow("/services/authors");
		assertEquals(4, httpMethods.size());
	}

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14");

		private static void startContainers() {
			Startables.deepStart(Stream.of(postgres)).join();
		}

		private static Map<String, String> createContextConfiguration() {
			return Map.of("spring.datasource.url", postgres.getJdbcUrl(), "spring.datasource.username",
					postgres.getUsername(), "spring.datasource.password", postgres.getPassword());
		}

		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			startContainers();
			ConfigurableEnvironment environment = applicationContext.getEnvironment();
			MapPropertySource testContainers = new MapPropertySource("testcontainers",
					(Map) createContextConfiguration());
			environment.getPropertySources().addFirst(testContainers);
		}
	}
}
