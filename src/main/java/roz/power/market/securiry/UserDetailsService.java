package roz.power.market.securiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import roz.power.market.crud.repository.repositories.postgres.UserRepository;
import roz.power.market.models.postgres.User;

import java.util.Optional;

@Component("securityUserDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if(optionalUser.isEmpty()) throw new UsernameNotFoundException("User not found");
        return new roz.power.market.securiry.UserDetails(optionalUser.get());
    }

}
