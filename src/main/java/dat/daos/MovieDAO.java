package dat.daos;

import dat.dtos.MovieDTO;
import dat.entities.Actor;
import dat.entities.Director;
import dat.entities.Genre;
import dat.entities.Movie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class MovieDAO {

	private final EntityManager entityManager;

	public MovieDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	// Save a list of movies into the database
	public void saveMovies(List<MovieDTO> movieDTOs) {
		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();

			for (MovieDTO movieDTO : movieDTOs) {
				// Convert MovieDTO to Movie entity
				Movie movie = convertToEntity(movieDTO);

				// Ensure the director, actors, and genres are saved first
				if (movie.getDirector() != null) {
					Director director = movie.getDirector();
					// Check if the director already exists in the database
					Director existingDirector = entityManager.find(Director.class, director.getId());
					if (existingDirector == null) {
						// Insert new director
						entityManager.persist(director);
					} else {
						movie.setDirector(existingDirector);
					}
				}

				// Persist actors and genres
				for (Actor actor : movie.getActors()) {
					Actor existingActor = entityManager.find(Actor.class, actor.getId());
					if (existingActor == null) {
						// Insert new actor
						entityManager.persist(actor);
					} else {
						actor = existingActor;
					}
				}

				for (Genre genre : movie.getGenres()) {
					Genre existingGenre = entityManager.find(Genre.class, genre.getId());
					if (existingGenre == null) {
						// Insert new genre
						entityManager.persist(genre);
					} else {
						genre = existingGenre;
					}
				}

				// Finally, persist the movie after ensuring all dependencies are saved
				entityManager.persist(movie);
			}

			transaction.commit();

		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			throw new RuntimeException("Failed to save movies: " + e.getMessage(), e);
		}
	}

	// Convert MovieDTO to Movie entity
	private Movie convertToEntity(MovieDTO movieDTO) {
		Movie movie = new Movie();
		movie.setId(movieDTO.getId());
		movie.setTitle(movieDTO.getTitle());
		movie.setOriginalLanguage(movieDTO.getOriginalLanguage());
		movie.setReleaseDate(movieDTO.getReleaseDate());
		movie.setPopularity(movieDTO.getPopularity());
		movie.setVoteAverage(movieDTO.getVoteAverage());

		// Convert and set actors
		List<Actor> actors = movieDTO.getActors().stream().map(actorDTO -> {
			Actor actor = new Actor();
			actor.setId(actorDTO.getId());
			actor.setName(actorDTO.getName());
			return actor;
		}).toList();
		movie.setActors(actors);

		// Check if the director is null before setting it
		if (movieDTO.getDirector() != null) {
			Director director = new Director();
			director.setId(movieDTO.getDirector().getId());
			director.setName(movieDTO.getDirector().getName());
			movie.setDirector(director);
		}

		// Convert and set genres
		List<Genre> genres = movieDTO.getGenres().stream().map(genreDTO -> {
			Genre genre = new Genre();
			genre.setId(genreDTO.getId());
			genre.setName(genreDTO.getName());
			return genre;
		}).toList();
		movie.setGenres(genres);

		return movie;
	}
}
