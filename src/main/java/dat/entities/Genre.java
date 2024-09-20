package dat.entities;

import dat.dtos.GenreDTO;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genres")
public class Genre {
	@Id
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@ToString.Exclude // Prevent infinite loop when fetching data
	@ManyToMany(mappedBy = "genres")
	private List<Movie> movies;

	public Genre (GenreDTO genreDTO) {
		this.id = genreDTO.getId();
		this.name = genreDTO.getName();
	}
}