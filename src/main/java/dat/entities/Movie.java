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

	@Column(name = "original_title")
	private String originalTitle;

	@Column(name = "release_date")
	private LocalDate releaseDate;

	@Column(name = "popularity")
	private Double popularity;

	@Column(name = "vote_average")
	private Double voteAverage;

	@Column(name = "vote_count")
	private Integer voteCount;

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

	public Movie(MovieDTO movieDTO) {
		this.id = movieDTO.getId();
		this.title = movieDTO.getTitle();
		this.originalLanguage = movieDTO.getOriginalLanguage();
		this.releaseDate = LocalDate.parse(movieDTO.getReleaseDate());
		this.originalTitle = movieDTO.getOriginalTitle();
		this.popularity = movieDTO.getPopularity();
		this.voteAverage = movieDTO.getVoteAverage();
		this.voteCount = movieDTO.getVoteCount();

		// Assume that each movie has only one director
		this.director = new Director(movieDTO.getDirectors().get(0));

		// Assume that each movie has at least one genre
		this.genres = new ArrayList<>();
		for (var genreDTO : movieDTO.getGenres()) {
			this.genres.add(new Genre(genreDTO));
		}

		// Assume that each movie has at least one actor
		this.actors = new ArrayList<>();
		for (var actorDTO : movieDTO.getActors()) {
			this.actors.add(new Actor(actorDTO));
		}
	}
}
