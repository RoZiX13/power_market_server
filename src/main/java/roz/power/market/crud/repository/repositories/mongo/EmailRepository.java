package roz.power.market.crud.repository.repositories.mongo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import roz.power.market.models.mongo.Email;

public interface EmailRepository extends MongoRepository<Email, Long> {
	Optional<Email> findByEmailAndCode(String email, String code);
	void deleteAllByEmail(String emailAddress);
	Optional<Email> findByEmail(String emailAddress);
}
