package dat.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dat.dtos.GenreDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genres")
public class Genre {
	@Id
	@Column(name = "genre_id", nullable = false)
	private Long genre_id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@ToString.Exclude // Prevent infinite loop when fetching data
	@ManyToMany(mappedBy = "genres")
	private List<Movie> movies;

	public Genre (GenreDTO genreDTO) {
		this.genre_id = genreDTO.getId();
		this.name = genreDTO.getName();
	}

}
