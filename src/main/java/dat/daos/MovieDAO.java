package dat.daos;

import dat.dtos.MovieDTO;
import dat.entities.Actor;
import dat.entities.Director;
import dat.entities.Genre;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.List;
import java.util.stream.Collectors;

public class MovieDAO implements IDAO<Movie> {

	public EntityManagerFactory emf;

	public MovieDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}

	// Method to save a list of movies to the database
	public void saveMovies (List<MovieDTO> movieDTOs) {
		EntityTransaction transaction = null;

		try (EntityManager em = emf.createEntityManager()) {
			transaction = em.getTransaction();
			transaction.begin();

			for (MovieDTO movieDTO : movieDTOs) {
				Movie movie = convertToEntity(movieDTO); // Convert MovieDTO to Movie entity

				// Ensure the director, actors, and genres are saved first
				if (movie.getDirector() != null) {
					Director director = movie.getDirector();
					// Check if the director already exists in the database
					Director existingDirector = em.find(Director.class, director.getId());
					if (existingDirector == null) {
						// Insert new director
						em.persist(director);
					} else {
						movie.setDirector(existingDirector);
					}
				}

				// Persist actors and genres
				for (Actor actor : movie.getActors()) {
					Actor existingActor = em.find(Actor.class, actor.getId());
					if (existingActor == null) {
						// Insert new actor
						em.persist(actor);
					} else {
						actor = existingActor;
					}
				}

				for (Genre genre : movie.getGenres()) {
					Genre existingGenre = em.find(Genre.class, genre.getId());
					if (existingGenre == null) {
						// Insert new genre
						em.persist(genre);
					} else {
						genre = existingGenre;
					}
				}

				// Finally, persist the movie after ensuring all dependencies are saved
				em.persist(movie);
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
	private Movie convertToEntity (MovieDTO movieDTO) {
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

	@Override
	public void create (Movie movie) {
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			em.persist(movie);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void read (Movie movie) {
	}


	@Override
	public void update (Movie movie) {
	}

	@Override
	public void delete (Movie movie) {
	}

	@Override
	public Movie findById (Long id) {
		return null;
	}

	@Override
	public List<Movie> findAll () {
		try (EntityManager em = emf.createEntityManager()) {
			Query query = em.createQuery("SELECT m FROM Movie m");
			List<Movie> movies = query.getResultList();
			return movies;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Search for a movie by genre (case-insensitive, partial match)
	public List<MovieDTO> searchForMovieByGenre(String genre) {
		try (EntityManager em = emf.createEntityManager()) {
			// JPQL query with case-insensitive search using LOWER() and LIKE
			Query query = em.createQuery(
					"SELECT m FROM Movie m JOIN m.genres g WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :genreName, '%'))");
			query.setParameter("genreName", genre);

			// Execute query and get the result list of movies
			List<Movie> movies = query.getResultList();

			// Convert List<Movie> to List<MovieDTO>
			return movies.stream()
					.map(this::convertToMovieDTO)
					.collect(Collectors.toList());

		} catch (Exception e) {
			e.printStackTrace();
			return null; // Or you can handle this better by throwing a custom exception
		}
	}

	// Method to convert Movie entity to MovieDTO
	private MovieDTO convertToMovieDTO(Movie movie) {
		return new MovieDTO(movie.getId(), movie.getTitle(), movie.getReleaseDate(), movie.getGenres());
	}

}
