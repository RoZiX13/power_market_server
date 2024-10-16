 package roz.power.market.securiry;

import java.util.Collection;
import java.util.Collections;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import roz.power.market.models.postgres.User;

public record UserDetails(User user) implements org.springframework.security.core.userdetails.UserDetails {

	 @Override
	 public Collection<? extends GrantedAuthority> getAuthorities() {
		 return Collections.singletonList(new SimpleGrantedAuthority("USER"));
	 }

	 @Override
	 public String getPassword() {
		 return this.user.getPassword();
	 }

	 @Override
	 public String getUsername() {
		 return this.user.getEmail();
	 }

	 @Override
	 public boolean isAccountNonExpired() {
		 return true;
	 }

	 @Override
	 public boolean isAccountNonLocked() {
		 return true;
	 }

	 @Override
	 public boolean isCredentialsNonExpired() {
		 return true;
	 }

	 @Override
	 public boolean isEnabled() {
		 return true;
	 }

 }
