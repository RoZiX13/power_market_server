package roz.power.market.models.postgres;

import java.util.Objects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

	@Id
	@Column(name = "task_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", length = 128)
	private String name;
	
	@Column(name = "description")
	private String description;

	@Column(name = "urlImage")
	private String urlImage;

	@ManyToOne()
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Task task = (Task) o;
		return id == task.id;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
