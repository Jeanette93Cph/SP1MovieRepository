package dat.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.Set;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "directors")
public class Director {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@OneToMany(mappedBy = "director")
	private Set<Movie> movies;
}
