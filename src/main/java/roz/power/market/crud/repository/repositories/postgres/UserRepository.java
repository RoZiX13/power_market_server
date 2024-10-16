package roz.power.market.crud.repository.repositories.postgres;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import roz.power.market.models.postgres.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByEmail(String email);
	Optional<User> findByEmailAndPassword(String email, String password);
}
