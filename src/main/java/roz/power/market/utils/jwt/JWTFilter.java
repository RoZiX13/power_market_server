package roz.power.market.utils.jwt;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter implements AuthenticateUser<String>{

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		if(header != null && !header.isBlank() && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			if(token.isBlank()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invailed JWT token");
			}else {
				Optional<Authentication> optionalAuthentication = authenticateUser(token);
                optionalAuthentication
						.ifPresent(authentication ->
								SecurityContextHolder.getContext()
										.setAuthentication(authentication));
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public Optional<Authentication> authenticateUser(String token){
		Authentication auth;
		try{
			String login = jwtUtil.validateTokenAndRetrieveClaim(token, "login");
			UserDetails userDetails = userDetailsService.loadUserByUsername(login);
			auth =
					new UsernamePasswordAuthenticationToken(userDetails,
							userDetails.getPassword(),
							userDetails.getAuthorities());

		}catch(Exception ex) {
			ex.fillInStackTrace();
			return Optional.empty();
		}
		return Optional.of(auth);
	}
}
