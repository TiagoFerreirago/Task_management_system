package com.th.nextdone.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.th.nextdone.exception.InvalidJwtAuthenticationException;
import com.th.nextdone.security.accountcredentials.TokenDto;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
@Service
public class JwtTokenProvider {

	@Value("${security.jwt.token.secretKey:secret}")
	private String secretKey;
	@Value("${security.jwt.token.expired-length:3600000}")
	private long validMilliseconds = 3600000;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	Algorithm algorithm = null;
	
	@PostConstruct
	protected void init() {
		
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		algorithm = Algorithm.HMAC256(secretKey.getBytes());
	}
	
	public TokenDto createAccessToken(String username, List<String>roles) {
		
		Date now = new Date();
		Date validity = new Date( now.getTime() + validMilliseconds);
		String accessToken = getAccessToken(username,roles,now,validity);
		String refreshToken = getRefreshToken(username,roles,now);
		
		return new TokenDto(username, true, now, validity, accessToken, refreshToken);
	}

	private String getRefreshToken(String username, List<String> roles, Date now) {

		Date refreshValidityTime = new Date(now.getTime() + validMilliseconds * 3);
		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(refreshValidityTime)
				.withSubject(username)
				.sign(algorithm);
				
	}

	private String getAccessToken(String username, List<String> roles, Date now, Date validity) {

		String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validity)
				.withSubject(username)
				.withIssuer(issuerUrl)
				.sign(algorithm);
				
	}
	
	private DecodedJWT decodedToken(String token) {
		
		Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
		JWTVerifier verifier = JWT.require(alg).build();
		DecodedJWT decoded = verifier.verify(token);
		return decoded;
	}
	
	public Authentication getAuthentication(String token) {
		DecodedJWT decoded = decodedToken(token);
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(decoded.getSubject());
		
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	public String resolveToken(HttpServletRequest request) throws Exception {
		
		String bearerToken = request.getHeader("Authorization");
		
			if(StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
				return bearerToken.substring("Bearer " .length());
			}
			else {
				throw new InvalidJwtAuthenticationException("Invalid JWTtoken");
			}
	}
	
	public boolean validateToken(String token) throws Exception {
		DecodedJWT decoded = decodedToken(token);
		
		try {
			if(decoded.getExpiresAt().before(new Date())) {
				return false;
			}
			return true;
		} catch (Exception e) {
			throw new InvalidJwtAuthenticationException("Expired or Invalid JWTtoken");		}
	}
}
