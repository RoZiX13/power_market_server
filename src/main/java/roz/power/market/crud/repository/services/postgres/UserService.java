package roz.power.market.crud.repository.services.postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import roz.power.market.crud.repository.repositories.mongo.EmailRepository;
import roz.power.market.crud.repository.repositories.postgres.UserRepository;
import roz.power.market.crud.repository.services.mongo.EmailService;
import roz.power.market.models.postgres.User;
import roz.power.market.utils.jwt.JwtUtil;

import java.util.*;

@Service
@Transactional(readOnly = true)
@PropertySource("classpath:application.properties")
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private JwtUtil jwtUtil;


	public Map<String, String> authorization(String login, String password){
		Optional<User> user = userRepository.findByEmail(login);
		if(user.isEmpty() ||
				passwordEncoder.encode(password).equals(user.get().getPassword()))
			throw new IllegalArgumentException();

		Map<String, String> mapToken = new HashMap<>();
		mapToken.put("token", jwtUtil.createToken(login));
		return mapToken;
	}

}
