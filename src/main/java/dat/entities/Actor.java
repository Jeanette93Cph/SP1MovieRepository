package dat.entities;

import dat.dtos.ActorDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "actors")
public class Actor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "department", nullable = false, length = 100)
	private String department;

	@Column(name = "popularity", nullable = false)
	private Double popularity;

	@Column(name = "character", nullable = false, length = 100)
	private String character;

	@ToString.Exclude // Prevent infinite loop when fetching data
	@ManyToMany(mappedBy = "actors")
	private Set<Movie> movies;

	public Actor(ActorDTO actorDTO) {
		this.id = actorDTO.getId();
		this.name = actorDTO.getName();
		this.department = actorDTO.getDepartment();
		this.popularity = actorDTO.getPopularity();
		this.character = actorDTO.getCharacter();
	}
}