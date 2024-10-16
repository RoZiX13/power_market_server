package roz.power.market.models.mongo;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Table(name = "email")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Email {
	
	@Id
	@Column(name = "id_email")
	private long id;

	@Column(name = "email")
	private String email;

	@Column(name = "message")
	private String message;

	@Column(name = "code")
	private String code;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Email email = (Email) o;
		return id == email.id;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
