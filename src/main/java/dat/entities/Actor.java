package dat.entities;

import dat.dtos.ActorDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "actors")
public class Actor {
	@Id
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@ToString.Exclude // Prevent infinite loop when fetching data
	@ManyToMany(mappedBy = "actors")
	private List<Movie> movies;

	public Actor (ActorDTO actorDTO) {
		this.id = actorDTO.getId();
		this.name = actorDTO.getName();
	}
}