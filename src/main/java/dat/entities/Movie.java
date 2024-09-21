package dat.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
public class Movie {

	@Id
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "title", nullable = false, length = 100)
	private String title;

	@Column(name = "original_language", nullable = false, length = 100)
	private String originalLanguage;

	@Column(name = "release_date", nullable = false, length = 10)
	private String releaseDate;

	@Column(name = "popularity", nullable = false)
	private Double popularity;

	@Column(name = "original_title", nullable = false, length = 100)
	private String originalTitle;

	@Column(name = "vote_average", nullable = false)
	private Double voteAverage;

	@ManyToMany
	@JoinTable(name = "movie_actors",
			joinColumns = @JoinColumn(name = "movie_id"),
			inverseJoinColumns = @JoinColumn(name = "actor_id"))
	private List<Actor> actors;

	@ManyToOne
	@JoinColumn(name = "director_id")
	private Director director;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "movie_genres",
			joinColumns = @JoinColumn(name = "movie_id"),
			inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private List<Genre> genres;
}