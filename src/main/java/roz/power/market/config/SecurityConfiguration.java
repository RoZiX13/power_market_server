package roz.power.market.config;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import roz.power.market.utils.jwt.JWTFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JWTFilter jwtFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
				.csrf(AbstractHttpConfigurer::disable)
				.cors(Customizer.withDefaults()) // Добавляем поддержку CORS
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/ws/*", "/auth", "/auth/login").permitAll()
						.requestMatchers("/user/*").hasRole("USER")
						.anyRequest().hasRole("ADMIN")
				)
				.formLogin(AbstractHttpConfigurer::disable)
				.sessionManagement(session ->
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		return daoAuthenticationProvider;
	}

    @Bean
    public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(BCryptVersion.$2B, 10, new SecureRandom());
	}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("*"); // Разрешить все источники
		config.addAllowedHeader("*"); // Разрешить все заголовки
		config.addAllowedMethod("*"); // Разрешить все методы (GET, POST, и т.д.)

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/*", config);
		return new CorsFilter(source);
	}
	
}
