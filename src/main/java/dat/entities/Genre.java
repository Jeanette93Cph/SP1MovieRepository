package dat.entities;

import dat.dtos.GenreDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genres")
public class Genre {
	@Id
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@ManyToMany(mappedBy = "genres")
	private List<Movie> movies;

	public Genre (GenreDTO genreDTO) {
		this.id = genreDTO.getId();
		this.name = genreDTO.getName();
	}
}