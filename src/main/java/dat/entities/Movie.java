package dat.entities;

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

	@Column(name = "rating")
	private Double rating;

	@Column(name = "popularity")
	private Double popularity;

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

	public Movie convertDTOToEntity(MovieDTO movieDTO) {
		Movie movie = new Movie();
		movie.setTitle(movieDTO.getTitle());
		movie.setReleaseDate(LocalDate.parse(movieDTO.getReleaseDate()));
		movie.setDirector(directorService.convertDTOToEntity(movieDTO.getDirector()));
		movie.setActors(movieDTO.getActors().stream()
				.map(actorService::convertDTOToEntity)
				.collect(Collectors.toList()));
		movie.setGenres(movieDTO.getGenres().stream()
				.map(genreService::convertDTOToEntity)
				.collect(Collectors.toList()));
		return movie;
	}
}
