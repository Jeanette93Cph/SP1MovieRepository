package dat.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dat.dtos.GenreDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genres")
public class Genre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@ToString.Exclude // Prevent infinite loop when fetching data
	@ManyToMany(mappedBy = "genres")
	private Set<Movie> movies;

	public Genre(GenreDTO genreDTO) {
		this.id = genreDTO.getId();
		this.name = genreDTO.getName();
	}


}
