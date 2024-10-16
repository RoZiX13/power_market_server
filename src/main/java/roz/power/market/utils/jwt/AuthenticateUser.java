package roz.power.market.utils.jwt;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface AuthenticateUser<T> {
    Optional<Authentication> authenticateUser(T t);
}
