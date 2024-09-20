package dat.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.dtos.ActorDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

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

	public Actor(ActorDTO actorDTO) {
		this.id = actorDTO.getId();
		this.name = actorDTO.getName();
	}
}