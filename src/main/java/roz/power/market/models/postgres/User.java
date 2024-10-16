package roz.power.market.models.postgres;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", length = 128)
	private String name;
	
	@Column(name = "surname", length = 128)
	private String surname;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email", unique = true)
	private String email;
	
	@OneToMany(mappedBy = "user")
	private Set<Task> tasks;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id == user.id;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
