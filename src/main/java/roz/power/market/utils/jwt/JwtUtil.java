package roz.power.market.utils.jwt;

import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JwtUtil {

	@Value("${spring.mail.username}")
	private String secret;
	
	public String createToken(String login) {
		
		Date expirationData = Date.from(ZonedDateTime.now().plusMinutes(1).toInstant());
		
		return JWT.create()
				.withSubject("power_market")
				.withClaim("login", login)
				.withIssuer("power_market_server")
				.withIssuedAt(new Date())
				.withExpiresAt(expirationData)
				.sign(Algorithm.HMAC512(secret));
	}		
	
	public String validateTokenAndRetrieveClaim(String token, String nameOfRelativeClaim) {
		JWTVerifier jwtVerifier  = JWT.require(Algorithm.HMAC512(secret))
				.withSubject("power_market")
				.withIssuer("power_market_server")
		.build();
		DecodedJWT jwt = jwtVerifier.verify(token);
		return jwt.getClaim(nameOfRelativeClaim).asString();
	}
	
}
