package roz.power.market.crud.repository.services.postgres;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import roz.power.market.crud.repository.repositories.postgres.UserRepository;
import roz.power.market.models.postgres.User;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Optional<User> optionalPerson =  userRepository.findByEmail(login);
		
		if(optionalPerson.isEmpty()) {
			throw new IllegalAccessError();
		}
		User user = optionalPerson.get();
		return new roz.power.market.securiry.UserDetails(user);
	}
}
