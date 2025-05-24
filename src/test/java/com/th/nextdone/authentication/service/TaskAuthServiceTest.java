package com.th.nextdone.authentication.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.th.nextdone.exception.CustomAccessDeniedException;
import com.th.nextdone.security.accountcredentials.AccountCredentialsDto;
import com.th.nextdone.security.accountcredentials.TokenDto;
import com.th.nextdone.security.jwt.JwtTokenProvider;
import com.th.nextdone.security.model.Permission;
import com.th.nextdone.security.model.User;
import com.th.nextdone.security.repository.UserRepository;
import com.th.nextdone.security.service.AuthService;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class TaskAuthServiceTest {
	
	@InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
    void testSignInSuccessWithValidAdminCredentials() {
		
		AccountCredentialsDto credentials = new AccountCredentialsDto();
		credentials.setUsername("admin");
		credentials.setPassword("123");
		
		Permission permission =new Permission();
		permission.setDescription("admin");
		
		User user = new User();
		user.setUsername("ADMIN");
		user.setPermissions(List.of(permission));
		
		when(userRepository.findByName("admin")).thenReturn(user);
		when(tokenProvider.createAccessToken(anyString(), anyList())).thenReturn(new TokenDto("name 1", false, Date.from(LocalDate.of(2025, 5, 10)
				.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.of(2025, 5, 12).atStartOfDay(
						ZoneId.systemDefault()).toInstant()), "hbc.jtw.token", "refresh.jwt.token"));
		
		ResponseEntity<TokenDto> response = authService.signIn(credentials);
		
		assertEquals("hbc.jtw.token", response.getBody().getAccessToken());
		assertEquals("name 1", response.getBody().getUsername());
		assertEquals(false, response.getBody().getAuthenticated());
		assertEquals(LocalDate.of(2025, 5, 10), response.getBody().getCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		assertEquals(LocalDate.of(2025, 5, 12), response.getBody().getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		assertEquals("refresh.jwt.token", response.getBody().getRefreshToken());
	}

	@Test
	void testCreateAccessThrowsExceptionForNonAdminUser() {
		
		User user = new User();
		user.setPermissions(List.of());
		
		Authentication auth = mock(Authentication.class);
		when(auth.getName()).thenReturn("admin");
		SecurityContextHolder.getContext().setAuthentication(auth);
		when(userRepository.findByName("admin")).thenReturn(user);
		
		AccountCredentialsDto credentials = new AccountCredentialsDto();
		credentials.setUsername("admin");
		credentials.setPassword("123");
		
		Exception ex = assertThrows(CustomAccessDeniedException.class, () -> {
			authService.createAccess(credentials);
		});
		
		String expectedMessage = "Only administrators can perform this action..";
		String generatedMessage = ex.getMessage();
		
		assertTrue(generatedMessage.contains(expectedMessage));
	}
	}
