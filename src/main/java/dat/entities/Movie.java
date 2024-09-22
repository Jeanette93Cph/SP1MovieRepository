package dat.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.dtos.ActorDTO;
import dat.dtos.MovieDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
public class Movie {
	@Id
	@Column(name = "movie_id", nullable = false)
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

	//Store genre_ids as a comma-separated String
	@Column(name = "genre_ids")
	private String genreIDs;


	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "director_id")
	private Director director;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "movie_genre",
			joinColumns = @JoinColumn(name = "movie_id"),
			inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private List<Genre> genres = new ArrayList<>();

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "movie_actor",
			joinColumns = @JoinColumn(name = "movie_id"),
			inverseJoinColumns = @JoinColumn(name = "actor_id"))
	private List<Actor> actors = new ArrayList<>();


//	public void addActor(Actor actor)
//	{
//		if(this.actors == null)
//		{
//			this.actors = new ArrayList<>();
//		}
//		this.actors.add(actor);
//		actor.getMovies().add(this);
//	}

	public void addActor(Actor actor) {
		if (this.actors == null) {
			this.actors = new ArrayList<>();
		}
		if (!this.actors.contains(actor)) {
			this.actors.add(actor);
			actor.getMovies().add(this);  // Add movie to actor's list
		}
	}




	public Movie(MovieDTO movieDTO) {
		this.id = movieDTO.getId();
		this.title = movieDTO.getTitle();
		this.originalLanguage = movieDTO.getOriginalLanguage();
		this.releaseDate = movieDTO.getReleaseDate();
		this.popularity = movieDTO.getPopularity();
		this.voteAverage = movieDTO.getVoteAverage();
		this.setGenreIDs(movieDTO.getGenreIDs());

		// Assume that each movie has only one director

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



	// treat genre_ids as a string to store it in the table 'movies' in separate column

	//setter method to store the genre IDs as a comma,separated string
	public void setGenreIDs(List<Long> genreIDs)
	{
		this.genreIDs = genreIDs != null ? String.join(",", genreIDs.stream().map(String::valueOf).toArray(String[]::new)) : null;
	}

	//getter method to retrieve genre IDs as a List
	public List<Long> getGenreIDs()
	{
		if(this.genreIDs != null && !this.genreIDs.isEmpty())
		{
			String[] ids = this.genreIDs.split(",");
			List<Long> genreIDList = new ArrayList<>();
			for(String id : ids)
			{
				genreIDList.add(Long.valueOf(id));
			}
			return genreIDList;
		}
		return new ArrayList<>();
	}




}
