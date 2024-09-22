package dat.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import dat.dtos.DirectorDTO;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "directors")
public class Director {

	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Id
	@Column(name = "director_id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@ToString.Exclude // Prevent infinite loop when fetching data
	@OneToMany(mappedBy = "director")
	private Set<Movie> movies;

	public Director(DirectorDTO directorDTO) {
		this.id = directorDTO.getId();
		this.name = directorDTO.getName();
	}
}
