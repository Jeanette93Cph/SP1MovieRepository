package dat.entities;

import dat.dtos.DirectorDTO;
import dat.dtos.MovieDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
public class Movie {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "original_language")
	private String originalLanguage;

	@Column(name = "release_date")
	private String releaseDate;

	@Column(name = "popularity")
	private Double popularity;

	@Column(name = "vote_average")
	private Double voteAverage;

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
		this.releaseDate = movieDTO.getReleaseDate();
		this.popularity = movieDTO.getPopularity();
		this.voteAverage = movieDTO.getVoteAverage();

		// Assume that each movie has only one director
		//this.director = new Director(movieDTO.getDirector());

		if (movieDTO.getDirector() != null) {
			this.director = new Director(movieDTO.getDirector());
		} else {
			this.director = null;
		}

		// Assume that each movie has at least one genre
		this.genres = new ArrayList<>();

		// Assume that each movie has at least one actor
		this.actors = new ArrayList<>();

	}



}
