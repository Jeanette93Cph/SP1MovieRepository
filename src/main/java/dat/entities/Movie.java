package dat.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import dat.dtos.ActorDTO;
import dat.dtos.DirectorDTO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "original_language")
	private String originalLanguage;

	@Column(name = "release_date")
	private LocalDate releaseDate;

	@Column(name = "vote_average")
	private double voteAverage;

	@ManyToOne
	@JoinColumn(name = "director_id")
	private Director director;

	@ManyToMany
	@JoinTable(name = "movie_genre",
			joinColumns = @JoinColumn(name = "movie_id"),
			inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private List<Genre> genres = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "movie_actor",
			joinColumns = @JoinColumn(name = "movie_id"),
			inverseJoinColumns = @JoinColumn(name = "actor_id"))
	private List<Actor> actors = new ArrayList<>();
}
